package openbankingservice.controller;

import lombok.RequiredArgsConstructor;
import openbankingservice.api.payments.PaymentConsentsApi;
import openbankingservice.models.payments.*;
import openbankingservice.service.PaymentConsentService;
import openbankingservice.util.StubData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class PaymentConsentsController implements PaymentConsentsApi {

    private final PaymentConsentService mPaymentConsentService;

    @Override
    public ResponseEntity<OBPaymentConsentTax1> createDomesticTaxConsents(
            final OBPaymentConsentTax body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ResponseEntity<OBPaymentConsentTax1> response =  mPaymentConsentService.createDomesticTaxConsents(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );

        final Random rand = new Random();
        //Получить случайный apiKey
        final String randApiKey = (String) StubData.CLIENTS.keySet().toArray()[rand.nextInt(StubData.CLIENTS.keySet().size())];
        //Случайно авторизовать либо отвергнуть
        if (rand.nextBoolean()) {
            mPaymentConsentService.authorizePaymentConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getDomesticTaxConsentId());
        } else {
            mPaymentConsentService.rejectConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getDomesticTaxConsentId());
        }
        return response;
    }

    @Override
    public ResponseEntity<OBPaymentConsent1> createPaymentConsents(
            @Valid final OBDomesticConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ResponseEntity<OBPaymentConsent1> response =  mPaymentConsentService.createDomesticConsents(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );

        final Random rand = new Random();
        //Получить случайный apiKey
        final String randApiKey = (String) StubData.CLIENTS.keySet().toArray()[rand.nextInt(StubData.CLIENTS.keySet().size())];
        //Случайно авторизовать либо отвергнуть
        if (rand.nextBoolean()) {
            mPaymentConsentService.authorizePaymentConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getDomesticConsentId());
        } else {
            mPaymentConsentService.rejectConsent(randApiKey, Objects.requireNonNull(response.getBody()).getData().getDomesticConsentId());
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> deletePaymentConsentsVPRByVPRConsentId(
            final String vrPConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.deletePaymentConsentsVPRByVPRConsentId(
                vrPConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentsConsentsList> getListOfPaymentsConsents(
            @NotNull @Valid final Date fromCreationDate,
            @NotNull @Valid final Date toCreationDate,
            @Valid final String type,
            @Valid final String status,
            final String xApiKey
    ) {
        return mPaymentConsentService.getListOfPaymentsConsents(
                fromCreationDate,
                toCreationDate,
                type,
                status,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsent2> getPaymentConsentsByPaymentConsentId(
            final String domesticConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentConsentsByPaymentConsentId(
                domesticConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentTax2> getPaymentConsentsDomesticTaxByPaymentConsentId(
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getDomesticTaxConsent(
                domesticTaxConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentListPassports2> getPaymentConsentsOfListPassportsByListPassportsConsentId(
            final String listPassportsConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentConsentsOfListPassportsByListPassportsConsentId(
                listPassportsConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentTaxReq2> getPaymentConsentsTaxRequirementByTaxRequirementConsentId(
            final String taxRequirementConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentConsentsTaxRequirementByTaxRequirementConsentId(
                taxRequirementConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBVRPConsent2> getPaymentConsentsVPRByVRPConsentId(
            final String vrPConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentConsentsVPRByVRPConsentId(
                vrPConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBReqConsent2> getPaymentsByRequirementId(
            final String requirementConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentsByRequirementId(
                requirementConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentListAccounts2> getPaymentsConsentsOfListAccountsByListAccountsConsentId(
            final String listAccountsConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.getPaymentsConsentsOfListAccountsByListAccountsConsentId(
                listAccountsConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentListPassports1> setPaymentConsentsByListPassports(
            @Valid final OBListPassportsConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.setPaymentConsentsByListPassports(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBReqConsent1> setPaymentConsentsRequrement(@Valid OBReqConsent body, String xIdempotencyKey, String xJwsSignature, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization, String xCustomerUserAgent, String xApiKey) {
        return null;
    }

// TODO: 13.08.2021   что-то не так тут не совпадает тело запроса и метод куда пробрасываем, просто заимплементирую метод из интерфейса. потом доделаем
   /* @Override
    public ResponseEntity<OBReqConsent1> setPaymentConsentsRequrement(
            @Valid final OBReqConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.setPaymentConsentsByListPassports(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    } */

    @Override
    public ResponseEntity<OBPaymentConsentTaxReq1> setPaymentConsentsTaxRequirement(
            @Valid final OBPaymentConsentTaxReq body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.setPaymentConsentsTaxRequirement(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBVRPConsent1> setPaymentConsentsVRP(
            @Valid final OBVRPConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.setPaymentConsentsVRP(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }

    @Override
    public ResponseEntity<OBPaymentConsentListAccounts1> setPaymentsConsentsOfListAccounts(
            @Valid final OBListAccountsConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        return mPaymentConsentService.setPaymentsConsentsOfListAccounts(
                body,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent,
                xApiKey
        );
    }
}
