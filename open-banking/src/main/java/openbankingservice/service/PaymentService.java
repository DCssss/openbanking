package openbankingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.data.entity.PaymentEntity;
import openbankingservice.data.repository.PaymentConsentRepository;
import openbankingservice.data.repository.PaymentRepository;
import openbankingservice.exception.OBException;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.PaymentConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;

import static openbankingservice.exception.OBErrorCode.BY_NBRB_UNEXPECTED_ERROR;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository mPaymentRepository;
    private final PaymentConsentRepository mPaymentConsentRepository;

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
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.DOMESTIC);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        final OBDataPayment1 data = new OBDataPayment1()
                .domesticId(payment.getId().toString())
                .domesticConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPayment1 response = new OBPayment1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

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
        payment.setType(TypePayment.DOMESTICTAX);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        final OBDataTaxPayment1 data = new OBDataTaxPayment1()
                .domesticTaxId(payment.getId().toString())
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
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

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        final OBDataTaxPayment2 data = new OBDataTaxPayment2()
                .domesticTaxId(payment.getId().toString())
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
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

    public ResponseEntity<OBPayment2> getDomesticByDomesticId(
            final String domesticId,
            final String domesticConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(domesticId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        final OBDataPayment2 data = new OBDataPayment2()
                .domesticId(payment.getId().toString())
                .domesticConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPayment2 response = new OBPayment2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
