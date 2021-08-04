package openbankingservice.service;

import openbankingservice.data.entity.ClientEntity;
import openbankingservice.data.entity.ConsentEntity;
import openbankingservice.data.repository.ConsentRepository;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.models.accinfo.AccountConsentsStatus;
import openbankingservice.models.accinfo.Consent;
import openbankingservice.models.accinfo.ConsentResponse;
import openbankingservice.models.accinfo.Permission;
import openbankingservice.util.ConsentConverter;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.StubData;
import openbankingservice.validation.ConsentBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class ConsentService {

    private final ConsentRepository mConsentRepository;

    private final FintechService mFintechService;
    private final ClientService mClientService;

    @Transactional
    public ResponseEntity<ConsentResponse> createConsent(
            @Valid @ConsentBody final Consent body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        final Date now = new Date();

        final ConsentEntity consent = ConsentConverter.toConsentEntity(body.getData());
        consent.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION);
        consent.setStatusUpdateTime(now);
        consent.setCreationTime(now);
        consent.setFintech(mFintechService.identifyFintech(xApiKey));

        mConsentRepository.save(consent);

        final ConsentResponse responseContent = new ConsentResponse()
                .data(ConsentConverter.toConsentResponseData(consent));

        return new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Void> authorizeConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = getConsent(Long.valueOf(xAccountConsentId));

        if (consent.getStatus() == AccountConsentsStatus.AWAITINGAUTHORISATION) {

            consent.setClient(client);
            consent.setStatus(AccountConsentsStatus.AUTHORISED);
            consent.setStatusUpdateTime(new Date());
            consent.getAccounts().addAll(client.getAccounts().stream().filter(account -> new Random().nextBoolean()).collect(Collectors.toList()));
            mConsentRepository.save(consent);

            final HttpHeaders headers = new HttpHeaders();
            headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Invalid consent status");
        }
    }

    @Transactional
    public ResponseEntity<Void> rejectConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = getConsent(Long.valueOf(xAccountConsentId));

        if (consent.getStatus() == AccountConsentsStatus.AWAITINGAUTHORISATION) {

            consent.setClient(client);
            consent.setStatus(AccountConsentsStatus.REJECTED);
            consent.setStatusUpdateTime(new Date());

            mConsentRepository.save(consent);

            final HttpHeaders headers = new HttpHeaders();
            headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

            return new ResponseEntity<>(headers, HttpStatus.OK);

        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Invalid consent status");
        }
    }

    public ResponseEntity<Void> revokeConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = getConsent(Long.valueOf(xAccountConsentId));

        if (consent.getStatus() == AccountConsentsStatus.AUTHORISED) {

            consent.setStatus(AccountConsentsStatus.REVOKED);
            consent.setStatusUpdateTime(new Date());

            mConsentRepository.save(consent);

            final HttpHeaders headers = new HttpHeaders();
            headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Invalid consent status");
        }
    }

    public ResponseEntity<ConsentResponse> getConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = getConsent(Long.valueOf(accountConsentId));

        final ConsentResponse consentResponse = new ConsentResponse()
                .data(ConsentConverter.toConsentResponseData(consent));

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(consentResponse, headers, HttpStatus.OK);

    }

    ConsentEntity checkPermissionAndGetConsent(
            final Long consentId,
            final String api
    ) {
        final ConsentEntity consent = getConsent(consentId);
        if (isHavePermission(consent, api)) {
            return consent;
        } else {
            throw new RuntimeException("Forbidden");
        }
    }

    private boolean isHavePermission(
            final ConsentEntity consent,
            final String api
    ) {
        for (Permission permission : consent.getPermission()) {
            if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                return true;
            }
        }

        return false;
    }

    private ConsentEntity getConsent(final Long id) {
        final ConsentEntity consent = mConsentRepository.getById(id);

        if (consent.getExpirationDate().before(new Date())) {
            //если дата согласия истекла, установить статус EXPIRED и сохранить
            consent.setStatus(AccountConsentsStatus.EXPIRED);
            mConsentRepository.save(consent);

            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Consent expired");
        }

        return consent;
    }
}