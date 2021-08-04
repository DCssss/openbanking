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

}
