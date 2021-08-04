package openbankingservice.controller;

import openbankingservice.api.accinfo.AccountConsentsApi;
import openbankingservice.models.accinfo.Consent;
import openbankingservice.models.accinfo.ConsentResponse;
import openbankingservice.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@RestController
@RequiredArgsConstructor
public class ConsentController implements AccountConsentsApi {

    private final ConsentService mConsentService;

    @Override
    public ResponseEntity<ConsentResponse> createConsent(
            @Valid final Consent body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        return mConsentService.createConsent(
                body,
                xFapiAuthDate,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<Void> authorizeConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.authorizeConsent(
                xFapiAuthDate,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                xApiKey,
                xAccountConsentId
        );
    }

    @Override
    public ResponseEntity<Void> rejectConsent(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.rejectConsent(
                xFapiAuthDate,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                xApiKey,
                xAccountConsentId
        );
    }

    @Override
    public ResponseEntity<Void> revokeConsent(
            final String xAccountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        return mConsentService.revokeConsent(xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<ConsentResponse> getConsentById(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey
    ) {
        return mConsentService.getConsentById(
                accountConsentId,
                xFapiAuthDate,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                xApiKey
        );
    }

}