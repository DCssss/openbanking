package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.Client;
import by.openbanking.openbankingservice.model.Consent;
import by.openbanking.openbankingservice.models.AccountConsentsStatus;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ClientRepository;
import by.openbanking.openbankingservice.repository.FintechRepository;
import by.openbanking.openbankingservice.util.StubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

@Service
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

    @Autowired
    public ConsentService(
            final ClientRepository clientRepository,
            final ConsentRepository consentRepository,
            final AccountRepository accountRepository,
            final FintechRepository fintechRepository
    ) {
        mClientRepository = clientRepository;
        mConsentRepository = consentRepository;
        mAccountRepository = accountRepository;
        mFintechRepository = fintechRepository;
    }

    @Transactional
    public ResponseEntity<Void> authorizeAccountConsents(
            final String accountConsentId,
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
        final Long clientId = StubData.CLIENTS.get(xApiKey);
        final Client client = mClientRepository.getById(clientId);

        final Optional<Consent> accountConsentsOptional = mConsentRepository.findById(Long.valueOf(accountConsentId));
        if (accountConsentsOptional.isPresent()) {

            final Consent consent = accountConsentsOptional.get();
            consent.setClient(client);
            consent.setStatus(AccountConsentsStatus.AUTHORISED);
            consent.setStatusUpdateTime(new Date());
            consent.getAccounts().addAll(mAccountRepository.findByClient(client));
            mConsentRepository.save(consent);

            response = new ResponseEntity<>(headers, HttpStatus.OK);

        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<Void> rejectAccountConsents(
            final String accountConsentId,
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
        final Optional<Consent> accountConsentsOptional = mConsentRepository.findById(Long.valueOf(accountConsentId));
        if (accountConsentsOptional.isPresent()) {

            final Consent consent = accountConsentsOptional.get();
            consent.setClient(client);
            //отклонить согласие
            consent.setStatus(AccountConsentsStatus.REJECTED);
            consent.setStatusUpdateTime(new Date());
            //сохранить изменения
            mConsentRepository.save(consent);
            response = new ResponseEntity<>(headers, HttpStatus.OK);

        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Transactional
    public ResponseEntity<OBReadConsentResponse1Post> createAccountConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1Post> response;
        try {
            final Consent consent = Consent.valueOf(body.getData());
            consent.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION);

            final Date now = new Date();
            consent.setStatusUpdateTime(now);
            consent.setCreationTime(now);
            consent.setFintech(mFintechRepository.getById(StubData.FINTECHS.get(xApiKey)));
            mConsentRepository.save(consent);

            final OBReadConsentResponse1Post responseContent = new OBReadConsentResponse1Post();
            responseContent.setData(consent.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<Void> deleteAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
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
        try {
            final Optional<Consent> accountConsentsOptional = mConsentRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {
                final Consent consent = accountConsentsOptional.get();
                consent.setStatus(AccountConsentsStatus.REVOKED);
                consent.setStatusUpdateTime(new Date());
                mConsentRepository.save(consent);

                response = new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<OBReadConsentResponse1> getAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
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
}
