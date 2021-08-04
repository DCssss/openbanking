package openbankingservice.controller;

import lombok.RequiredArgsConstructor;
import openbankingservice.api.payments.PaymentsApi;
import openbankingservice.models.payments.*;
import openbankingservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentsApi {

    private final PaymentService mPaymentService;

    @Override
    public ResponseEntity<OBPayment1> createDomesticPayment(
            @Valid final OBDomesticPayment body,
            final String domesticConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentService.createDomesticPayment(
                body,
                domesticConsentId,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent
        );
    }

    @Override
    public ResponseEntity<OBTaxPayment1> createDomesticTaxPayment(
            @Valid final OBDomesticTaxPayment body,
            final String domesticTaxConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentService.createDomesticTaxPayment(
                body,
                domesticTaxConsentId,
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent
        );
    }

    @Override
    public ResponseEntity<OBPayment2> getDomesticPaymentByDomesticId(
            final String domesticId,
            final String domesticConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentService.getDomesticByDomesticId(
                domesticId,
                domesticConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent
        );
    }

    @Override
    public ResponseEntity<OBTaxPayment2> getDomesticTaxByDomesticTaxId(
            final String domesticTaxId,
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        return mPaymentService.getDomesticTaxByDomesticTaxId(
                domesticTaxId,
                domesticTaxConsentId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xCustomerUserAgent
        );
    }

    @Override
    public ResponseEntity<OBPaymentsList> getListOfPayments(@NotNull @Valid final Date fromCreationDate, @NotNull @Valid final Date toCreationDate, @Valid final String type, @Valid final String status) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentListPassports2> getPaymentsByListPassportsId(final String listPassportsId, final String listPassportsConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentListAccounts2> getPaymentsListAccountsByListAccountsId(final String listAccountsId, final String listAccountsConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentReq2> getPaymentsRequirimentsByRequirementId(final String requirementId, final String requirementConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBTaxPaymentReq2> getPaymentsTaxRequirementsByTaxRequirementId(final String taxRequirementId, final String taxRequirementConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBVRP2> getPaymentsVRPbyVRPid(final String vrPId, final String vrPConsentId, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentListPassports1> setPaymentsByListPassports(@Valid final OBListPassportsPayment body, final String listPassportsConsentId, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentListAccounts1> setPaymentsListAccounts(@Valid final OBListAccountsPayment body, final String listAccountsConsentId, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBPaymentReq1> setPaymentsRequirements(@Valid final OBReqPayment body, final String requirementConsentId, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBTaxPaymentReq1> setPaymentsTaxRequirements(@Valid final OBReqTaxPayment body, final String taxRequirementConsentId, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }

    @Override
    public ResponseEntity<OBVRP1> setPaymentsVRP(@Valid final OBVRPPayment body, final String vrPConsentId, final String xIdempotencyKey, final String xJwsSignature, final String xFapiAuthDate, final String xFapiCustomerIpAddress, final String xFapiInteractionId, final String authorization, final String xCustomerUserAgent) {
        return null;
    }
}
