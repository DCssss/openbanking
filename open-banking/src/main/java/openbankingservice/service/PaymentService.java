package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.data.entity.PaymentEntity;
import openbankingservice.data.repository.PaymentConsentRepository;
import openbankingservice.data.repository.PaymentRepository;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.PaymentConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository mPaymentRepository;
    private final PaymentConsentRepository mPaymentConsentRepository;

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
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticTaxConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(PaymentEntity.Type.DOMESTIC_TAX);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final OBDataTaxPayment1 data = new OBDataTaxPayment1()
                .domesticTaxId(payment.getId().toString())
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBTaxPayment1 response = new OBTaxPayment1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBTaxPayment2> getDomesticTaxByDomesticTaxId(
            final String domesticTaxId,
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticTaxConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(domesticTaxId));

        final OBDataTaxPayment2 data = new OBDataTaxPayment2()
                .domesticTaxId(payment.getId().toString())
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBTaxPayment2 response = new OBTaxPayment2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
