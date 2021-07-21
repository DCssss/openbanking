package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.AccountConsentsApi;
import by.openbanking.openbankingservice.models.CreateConsentRequestModel;
import by.openbanking.openbankingservice.models.CreateConsentResponseModel;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
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
    public ResponseEntity<CreateConsentResponseModel> createConsent(
            @Valid final CreateConsentRequestModel body,
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
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mConsentService.revokeConsent(xFapiAuthDate, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadConsentResponse1> getConsentById(
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
