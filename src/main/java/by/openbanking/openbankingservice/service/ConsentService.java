package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.Client;
import by.openbanking.openbankingservice.entity.Consent;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ClientRepository;
import by.openbanking.openbankingservice.repository.FintechRepository;
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

    private static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    private static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final String AUTHORIZATION = "authorization";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";

    private final ClientRepository mClientRepository;
    private final ConsentRepository mConsentRepository;
    private final AccountRepository mAccountRepository;
    private final FintechRepository mFintechRepository;
    private final ClientService mClientService;

    @Transactional
    public ResponseEntity<CreateConsentResponseBody> createConsent(
            @Valid final CreateConsentRequestBody body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<CreateConsentResponseBody> response;
        try {
            final Consent consent = Consent.valueOf(body.getData());
            consent.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION);

            final Date now = new Date();
            consent.setStatusUpdateTime(now);
            consent.setCreationTime(now);
            consent.setFintech(mFintechRepository.getById(StubData.FINTECHS.get(xApiKey)));
            mConsentRepository.save(consent);

            final CreateConsentResponseBody responseContent = new CreateConsentResponseBody();
            responseContent.setData(consent.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
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
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final Client client = mClientService.findClient(xApiKey);

        final Consent consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));

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
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(xApiKey);
        final Client client = mClientRepository.getById(clientId);


        //получить согласие
        final Consent consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));

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
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final Consent consent = mConsentRepository.getById(Long.valueOf(xAccountConsentId));
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

    public ResponseEntity<OBReadConsentResponse1> getConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1> response;

        final Optional<Consent> accountConsentsData = mConsentRepository.findById(Long.valueOf(accountConsentId));

        if (accountConsentsData.isPresent()) {
            final OBReadConsentResponse1 obReadConsentResponse1 = new OBReadConsentResponse1();
            obReadConsentResponse1.setData(accountConsentsData.get().toOBReadConsentResponse1Data());

            response = new ResponseEntity<>(obReadConsentResponse1, headers, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        return response;
    }

    public boolean isHaveConsent(
            final Long consentId,
            final String api
    ) {
        //найти согласие
        final Optional<Consent> optionalConsent = mConsentRepository.findById(consentId);

        if (optionalConsent.isPresent()) {
            final Consent consent = optionalConsent.get();

            //если такое согласие существует, проверить дату согласия
            if (consent.getExpirationDate().after(new Date())) {
                //если согласие актуально, проверить дает ли оно право на запрашиваемое АПИ
                for (Permission permission : consent.getPermission()) {
                    if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                        return true;
                    }
                }

            } else {
                //если дата согласия истекла, установить статус EXPIRED и сохранить
                consent.setStatus(AccountConsentsStatus.EXPIRED);
                mConsentRepository.save(consent);
            }

            return false;
        } else {
            return false;
        }
    }
}
