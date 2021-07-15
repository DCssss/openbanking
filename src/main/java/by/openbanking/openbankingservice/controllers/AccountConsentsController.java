package by.openbanking.openbankingservice.controllers;

import by.openbanking.openbankingservice.api.AccountConsentsApi;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.service.AccountConsentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@RestController
public final class AccountConsentsController implements AccountConsentsApi {

    private final AccountConsentsService mAccountConsentsService;

    @Autowired
    public AccountConsentsController(
            final AccountConsentsService service
    ) {
        mAccountConsentsService = service;
    }

    @Override
    public ResponseEntity<Void> authorizeAccountConsents(
            final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String apikey
    ) {
        return mAccountConsentsService.authorizeAccountConsents(accountConsentId, xFapiInteractionId, apikey);
    }

    @Override
    public ResponseEntity<Void> rejectAccountConsents(
            final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String apikey
    ) {
        return mAccountConsentsService.rejectAccountConsents(accountConsentId, xFapiInteractionId, apikey);
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1Post> createAccountConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        return mAccountConsentsService.createAccountConsents(body, xFapiInteractionId);
    }

    @Override
    public ResponseEntity<Void> deleteAccountConsent(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        return mAccountConsentsService.deleteAccountAccessConsentsConsentId(accountConsentId, xFapiInteractionId);
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1> getAccountConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        return mAccountConsentsService.getAccountAccessConsentsConsentId(accountConsentId, xFapiInteractionId);
    }
}
