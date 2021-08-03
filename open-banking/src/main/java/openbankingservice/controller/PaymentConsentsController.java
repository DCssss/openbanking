package openbankingservice.controller;

import lombok.RequiredArgsConstructor;
import openbankingservice.api.payments.PaymentConsentsApi;
import openbankingservice.models.payments.*;
import openbankingservice.service.PaymentConsentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PaymentConsentsController implements PaymentConsentsApi {

    private final PaymentConsentService mPaymentConsentService;

    @Override
    public ResponseEntity<OBPaymentConsentTax1> createDomesticTaxConsents(
            @Valid final OBPaymentConsentTax body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentConsentService.createDomesticTaxConsents(body, xIdempotencyKey, xJwsSignature, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xCustomerUserAgent);
    }

    @Override
    public ResponseEntity<Void> deletePaymentConsentsVPRByVPRConsentId(final String vrPConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentTax2> getPaymentConsentsDomesticTaxByPaymentConsentId(
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentConsentService.getDomesticTaxConsent(
                domesticTaxConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent);
    }

    @Override
    public ResponseEntity<OBPaymentConsentListPassports2> getPaymentConsentsOfListPassportsByListPassportsConsentId(final String listPassportsConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentTaxReq2> getPaymentConsentsTaxRequirementByTaxRequirementConsentId(final String taxRequirementConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBVRPConsent2> getPaymentConsentsVPRByVRPConsentId(final String vrPConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBReqConsent2> getPaymentsByRequirementId(final String requirementConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentListAccounts2> getPaymentsConsentsOfListAccountsByListAccountsConsentId(final String listAccountsConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentListPassports1> setPaymentConsentsByListPassports(@Valid final OBListPassportsConsent body, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBReqConsent1> setPaymentConsentsRequrement(@Valid final OBReqConsent body, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentTaxReq1> setPaymentConsentsTaxRequirement(@Valid final OBPaymentConsentTaxReq body, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBVRPConsent1> setPaymentConsentsVRP(@Valid final OBVRPConsent body, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentConsentListAccounts1> setPaymentsConsentsOfListAccounts(@Valid final OBListAccountsConsent body, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }
}
