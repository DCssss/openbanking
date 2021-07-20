package by.openbanking.openbankingservice.controllers;

import by.openbanking.openbankingservice.api.AccountConsentsApi;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@RestController
@RequiredArgsConstructor
public final class ConsentController implements AccountConsentsApi {

    private final ConsentService mConsentService;

    @Override
    public ResponseEntity<Void> authorizeAccountConsents(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.authorizeAccountConsents(xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<Void> rejectAccountConsents(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.rejectAccountConsents(xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1Post> createAccountConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        return mConsentService.createAccountConsents(body, xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey);
    }

    @Override
    public ResponseEntity<Void> deleteAccountConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.deleteAccountAccessConsentsConsentId(xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1> getAccountConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        return mConsentService.getAccountAccessConsentsConsentId(accountConsentId, xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey);
    }
}
