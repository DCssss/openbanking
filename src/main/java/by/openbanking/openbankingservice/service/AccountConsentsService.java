package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import by.openbanking.openbankingservice.util.StubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountConsentsService {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    private final AccountConsentsRepository mRepository;

    @Autowired
    public AccountConsentsService(
            final AccountConsentsRepository repository
    ) {
        mRepository = repository;
    }

    public ResponseEntity<Void> authorizeAccountConsents(
            final String accountConsentId,
            final String xFapiInteractionId,
            final String apikey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final Long clientId = StubData.CLIENTS.get(apikey);

        if (clientId != null) {

            final Optional<AccountConsents> accountConsentsOptional = mRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {

                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setClientId(clientId);
                accountConsents.setStatus(AccountConsentsStatus.AUTHORISED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mRepository.save(accountConsents);
                response = new ResponseEntity<>(headers, HttpStatus.OK);

            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    public ResponseEntity<Void> rejectAccountConsents(
            final String accountConsentId,
            final String xFapiInteractionId,
            final String apikey
    ) {

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(apikey);

        if (clientId != null) {

            //получить согласие
            final Optional<AccountConsents> accountConsentsOptional = mRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {

                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setClientId(clientId);
                //отклонить согласие
                accountConsents.setStatus(AccountConsentsStatus.REJECTED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                //сохранить изменения
                mRepository.save(accountConsents);
                response = new ResponseEntity<>(headers, HttpStatus.OK);

            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<OBReadConsentResponse1Post> createAccountAccessConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1Post> response;
        try {
            final AccountConsents accountConsents = AccountConsents.valueOf(body.getData());
            accountConsents.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION.toString());

            final Date now = new Date();
            accountConsents.setStatusUpdateTime(now);
            accountConsents.setCreationTime(now);
            mRepository.save(accountConsents);

            final OBReadConsentResponse1Post responseContent = new OBReadConsentResponse1Post();
            responseContent.setData(accountConsents.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<Void> deleteAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        try {
            final Optional<AccountConsents> accountConsentsOptional = mRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {
                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setStatus(AccountConsentsStatus.REVOKED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mRepository.save(accountConsents);

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
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1> response;

        final Optional<AccountConsents> accountConsentsData = mRepository.findById(Long.valueOf(accountConsentId));

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
