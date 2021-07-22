package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.ClientEntity;
import by.openbanking.openbankingservice.entity.ConsentEntity;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ClientRepository;
import by.openbanking.openbankingservice.repository.FintechRepository;
import by.openbanking.openbankingservice.util.ConsentConverter;
import by.openbanking.openbankingservice.util.OBHttpHeaders;
import by.openbanking.openbankingservice.util.StubData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ClientRepository mClientRepository;
    private final ConsentRepository mConsentRepository;
    private final FintechRepository mFintechRepository;
    private final ClientService mClientService;

    @Transactional
    public ResponseEntity<ConsentResponse> createConsent(
            @Valid final Consent body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        final ConsentEntity consent = ConsentEntity.valueOf(body.getData());
        consent.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION);

        final Date now = new Date();
        consent.setStatusUpdateTime(now);
        consent.setCreationTime(now);
        consent.setFintech(mFintechRepository.getById(StubData.FINTECHS.get(xApiKey)));
        mConsentRepository.save(consent);

        final ConsentResponse responseContent = new ConsentResponse();
        responseContent.setData(ConsentConverter.toConsentResponseData(consent));

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
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final ClientEntity client = mClientService.identifyClient(xApiKey);

        final ConsentEntity consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));

        if (consent.getStatus() == AccountConsentsStatus.AWAITINGAUTHORISATION) {
            consent.setClient(client);
            consent.setStatus(AccountConsentsStatus.AUTHORISED);
            consent.setStatusUpdateTime(new Date());
            consent.getAccounts().addAll(client.getAccounts().stream().filter(account -> new Random().nextBoolean()).collect(Collectors.toList()));
            mConsentRepository.save(consent);

            response = new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            throw new IllegalStateException("Consent status not AwaitingAuthorisation");
        }

        return response;
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

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(xApiKey);
        final ClientEntity client = mClientRepository.getById(clientId);


        //получить согласие
        final ConsentEntity consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));

        if (consent.getStatus() == AccountConsentsStatus.AWAITINGAUTHORISATION) {

            consent.setClient(client);
            //отклонить согласие
            consent.setStatus(AccountConsentsStatus.REJECTED);
            consent.setStatusUpdateTime(new Date());
            //сохранить изменения
            mConsentRepository.save(consent);
            response = new ResponseEntity<>(headers, HttpStatus.OK);

        } else {
            throw new IllegalStateException("Consent status not AwaitingAuthorisation");
        }

        return response;
    }

    public ResponseEntity<Void> revokeConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final ConsentEntity consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));
        if (consent.getStatus() == AccountConsentsStatus.AUTHORISED) {

            consent.setStatus(AccountConsentsStatus.REVOKED);
            consent.setStatusUpdateTime(new Date());
            mConsentRepository.save(consent);

            response = new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            throw new IllegalStateException("Consent status not Authorized");
        }

        return response;
    }

    public ResponseEntity<ConsentResponse> getConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        final ConsentEntity consent = mConsentRepository.getById(Long.valueOf(accountConsentId));

        final ConsentResponse consentResponse = new ConsentResponse();
        consentResponse.setData(ConsentConverter.toConsentResponseData(consent));

        return new ResponseEntity<>(consentResponse, headers, HttpStatus.OK);

    }

    ConsentEntity checkPermissionAndGetConsent(
            final Long consentId,
            final String api
    ){
        final ConsentEntity consent = getConsent(consentId);
        if (isHavePermission(consent, api)){
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

            throw new IllegalStateException("Consent expired");
        }

        return consent;
    }
}
