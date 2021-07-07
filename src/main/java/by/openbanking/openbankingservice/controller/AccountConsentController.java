package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.AccountConsentsApi;
import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Data;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;


@RestController
public final class AccountConsentController implements AccountConsentsApi {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    private final AccountConsentsRepository accountConsentsRepository;

    @Autowired
    public AccountConsentController(AccountConsentsRepository repository) {
        this.accountConsentsRepository = repository;
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1Post> createAccountAccessConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1Post> response;
        try {
            final AccountConsents accountConsents = AccountConsents.valueOf(body.getData());
            accountConsents.setAccountConsentStatus(OBReadConsentResponse1Data.StatusEnum.AUTHORISED.toString());

            final Date now = new Date();
            accountConsents.setStatusUpdateTime(now);
            accountConsents.setCreationTime(now);
            accountConsentsRepository.save(accountConsents);

            final OBReadConsentResponse1Post responseContent = new OBReadConsentResponse1Post();
            responseContent.setData(accountConsents.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public ResponseEntity<Void> deleteAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        try {
            accountConsentsRepository.deleteById(Long.valueOf(accountConsentId));
            response = new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1> getAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1> response;

        final Optional<AccountConsents> accountConsentsData = accountConsentsRepository.findById(Long.valueOf(accountConsentId));

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
