package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.data.repository.PaymentConsentRepository;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.PaymentConsentConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class PaymentConsentService {

    private final PaymentConsentRepository mPaymentConsentRepository;

    private final FintechService mFintechService;

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
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataTax data = PaymentConsentConverter.toOBDataTax(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBPaymentConsentTax1 response = new OBPaymentConsentTax1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentTax2> getDomesticTaxConsent(
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticTaxConsentId));

        final OBDataTax1 data = PaymentConsentConverter.toOBDataTax1(paymentConsentEntity);

        final OBPaymentConsentTax2 response = new OBPaymentConsentTax2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsent1> createDomesticConsents(
            @Valid final OBDomesticConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataDomesticResp data = PaymentConsentConverter.toOBDataDomesticResp(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBPaymentConsent1 response = new OBPaymentConsent1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsent2> getPaymentConsentsByPaymentConsentId(
            final String domesticConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticConsentId));

        final OBDataDomesticResp1 data = PaymentConsentConverter.toOBDataDomesticResp1(paymentConsentEntity);

        final OBPaymentConsent2 response = new OBPaymentConsent2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentListAccounts1> setPaymentsConsentsOfListAccounts(
            @Valid final OBListAccountsConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataListAccountsResp data = PaymentConsentConverter.toOBDataListAccountsResp(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBPaymentConsentListAccounts1 response = new OBPaymentConsentListAccounts1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentListAccounts2> getPaymentsConsentsOfListAccountsByListAccountsConsentId(
            final String listAccountsConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));

        final OBDataListAccountsResp1 data = PaymentConsentConverter.toOBDataListAccountsResp1(paymentConsentEntity);

        final OBPaymentConsentListAccounts2 response = new OBPaymentConsentListAccounts2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentListPassports1> setPaymentConsentsByListPassports(
            @Valid final OBListPassportsConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataListPassportsResp data = PaymentConsentConverter.toOBDataListPassportsResp(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBPaymentConsentListPassports1 response = new OBPaymentConsentListPassports1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentListPassports2> getPaymentConsentsOfListPassportsByListPassportsConsentId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataListPassportsResp1 data = PaymentConsentConverter.toOBDataListPassportsResp1(paymentConsentEntity);

        final OBPaymentConsentListPassports2 response = new OBPaymentConsentListPassports2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBReqConsent1> setPaymentConsentsByListPassports(
            @Valid final OBReqConsent body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataReqResp data = PaymentConsentConverter.toOBDataReqResp(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBReqConsent1 response = new OBReqConsent1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBReqConsent2> getPaymentsByRequirementId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataReqResp1 data = PaymentConsentConverter.toOBDataReqResp1(paymentConsentEntity);

        final OBReqConsent2 response = new OBReqConsent2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentTaxReq1> setPaymentConsentsTaxRequirement(
            @Valid final OBPaymentConsentTaxReq body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataTaxReq1 data = PaymentConsentConverter.toOBDataTaxReq1(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());

        final OBPaymentConsentTaxReq1 response = new OBPaymentConsentTaxReq1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentConsentTaxReq2> getPaymentConsentsTaxRequirementByTaxRequirementConsentId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataTaxReq2 data = PaymentConsentConverter.toOBDataTaxReq2(paymentConsentEntity);

        final OBPaymentConsentTaxReq2 response = new OBPaymentConsentTaxReq2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
