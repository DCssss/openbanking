package openbankingservice.controller;

import lombok.RequiredArgsConstructor;
import openbankingservice.api.accinfo.AccountConsentsApi;
import openbankingservice.models.accinfo.Consent;
import openbankingservice.models.accinfo.ConsentResponse;
import openbankingservice.service.ConsentService;
import openbankingservice.util.StubData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Random;


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
            @RequestHeader(value = "authorization", required = true)  String authorization,
            final String xApiKey
    ) {
        final ResponseEntity<ConsentResponse> response = mConsentService.createConsent(
                body,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xApiKey
        );

        final Random rand = new Random();
        //Получить случайный apiKey
        final String randApiKey = (String) StubData.CLIENTS.keySet().toArray()[rand.nextInt(StubData.CLIENTS.keySet().size())];
        //Случайно авторизовать либо отвергнуть
        if (rand.nextBoolean()) {
            mConsentService.authorizeConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getAccountConsentId());
        } else {
            mConsentService.rejectConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getAccountConsentId());
        }

        return response;
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
