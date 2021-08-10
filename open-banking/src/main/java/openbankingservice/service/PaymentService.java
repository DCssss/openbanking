package openbankingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.AccountEntity;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.data.entity.PaymentEntity;
import openbankingservice.data.entity.TransactionEntity;
import openbankingservice.data.repository.*;
import openbankingservice.exception.OBException;
import openbankingservice.models.accinfo.OBCreditDebitCode1;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static openbankingservice.data.entity.PaymentEntity.Status.ACCC;
import static openbankingservice.data.entity.PaymentEntity.Status.RJCT;
import static openbankingservice.exception.OBErrorCode.BY_NBRB_FIELD_INVALID_DATE;
import static openbankingservice.exception.OBErrorCode.BY_NBRB_UNEXPECTED_ERROR;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository mPaymentRepository;
    private final PaymentConsentRepository mPaymentConsentRepository;
    private final AccountRepository mAccountRepository;
    private final TransactionRepository mTransactionRepository;
    private final AccountService mAccountService;
    private final BankRepository mBankRepository;

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
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
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
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
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

    public ResponseEntity<OBPaymentListAccounts1> createListAccountsPayment(
            @Valid final OBListAccountsPayment body,
            final String listAccountsConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.LISTACCOUNTS);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListAccounts initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListAccounts.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentListAccounts1 data = new OBDataPaymentListAccounts1()
                .listAccountsId(payment.getId().toString())
                .listAccountsConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentListAccounts1 response = new OBPaymentListAccounts1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentListPassports1> createListPassportsPayment(
            @Valid final OBListPassportsPayment body,
            final String paymentConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.LISTPASSPORTS);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListPassports initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListPassports.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentListPassports1 data = new OBDataPaymentListPassports1()
                .listPassportsId(payment.getId().toString())
                .listPassportsConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentListPassports1 response = new OBPaymentListPassports1()
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
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
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
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
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

    public ResponseEntity<OBPaymentListAccounts2> getPaymentsListAccountsByListAccountsId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListAccounts initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListAccounts.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentListAccounts2 data = new OBDataPaymentListAccounts2()
                .listAccountsId(payment.getId().toString())
                .listAccountsConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentListAccounts2 response = new OBPaymentListAccounts2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentListPassports2> getPaymentListPassports(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListPassports initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListPassports.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentListPassports2 data = new OBDataPaymentListPassports2()
                .listPassportsId(payment.getId().toString())
                .listPassportsConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentListPassports2 response = new OBPaymentListPassports2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentReq1> createRequirementPayment(
            @Valid final OBReqPayment body,
            final String listAccountsConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.REQUIREMENT);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentReq1 data = new OBDataPaymentReq1()
                .requirementId(payment.getId().toString())
                .requirementConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentReq1 response = new OBPaymentReq1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentReq2> getPaymentsRequirimentsByRequirementId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataPaymentReq2 data = new OBDataPaymentReq2()
                .requirementId(payment.getId().toString())
                .requirementConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBPaymentReq2 response = new OBPaymentReq2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBTaxPaymentReq1> createTaxRequirementPayment(
            @Valid final OBReqTaxPayment body,
            final String listAccountsConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.REQUIREMENT);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataTaxPaymentReq1 data = new OBDataTaxPaymentReq1()
                .taxRequirementId(payment.getId().toString())
                .taxRequirementConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBTaxPaymentReq1 response = new OBTaxPaymentReq1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBTaxPaymentReq2> getPaymentsTaxRequirementsByTaxRequirementId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataTaxPaymentReq2 data = new OBDataTaxPaymentReq2()
                .taxRequirementId(payment.getId().toString())
                .taxRequirementConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBTaxPaymentReq2 response = new OBTaxPaymentReq2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBVRP1> setPaymentsVRP(
            @Valid final OBVRPPayment body,
            final String listAccountsConsentId,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));
        final Date now = new Date();
        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsentEntity);
        payment.setType(TypePayment.VRP);
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationVRP initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationVRP.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataVRP1 data = new OBDataVRP1()
                .vrPId(payment.getId().toString())
                .vrPConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatus()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBVRP1 response = new OBVRP1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBVRP2> getPaymentsVRPbyVRPid(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));
        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationVRP initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationVRP.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        final OBDataVRP2 data = new OBDataVRP2()
                .vrPId(payment.getId().toString())
                .vrPConsentId(paymentConsentEntity.getId().toString())
                .creationDateTime(payment.getCreateTime())
                .initiation(initiation)
                .paymentStatus(
                        new OBDataPaymentStatusGet()
                                .statusUpdateDateTime(payment.getStatusUpdateTime())
                                .paymentStatus(payment.getStatus().toString()));

        final OBVRP2 response = new OBVRP2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentsList> getListOfPayments(
            @NotNull @Valid final Date fromCreationDate,
            @NotNull @Valid final Date toCreationDate,
            @Valid final String type,
            @Valid final String status
    ) {
        if (fromCreationDate.after(toCreationDate)) {
            throw new OBException(BY_NBRB_FIELD_INVALID_DATE, "fromCreationDate must be before toCreationDate", "fromCreationDate");
        }

        final List<PaymentEntity> payments = mPaymentRepository.findAllByCreateTimeBetweenAndTypeAndStatus(fromCreationDate, toCreationDate, TypePayment.fromValue(type), PaymentEntity.Status.valueOf(status));

        final OBPayment obPayment = new OBPayment();
        obPayment.addAll(payments);

        final OBDataPaymentsList data = new OBDataPaymentsList()
                .payment(obPayment);

        final OBPaymentsList response = new OBPaymentsList()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional
    public void makePayment(final String paymentId) {

        final Date now = new Date();

        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));
        final Optional<AccountEntity> debtorAccountOptional = mAccountRepository.findByIdentification(payment.getPaymentConsent().getDebtorAccId());
        final Optional<AccountEntity> creditorAccountOptional = mAccountRepository.findByIdentification(payment.getPaymentConsent().getCreditorAccId());
        //Проверка на существование счетов
        if (debtorAccountOptional.isPresent()
                && creditorAccountOptional.isPresent()) {

            final AccountEntity debtorAccount = debtorAccountOptional.get();
            final AccountEntity creditorAccount = creditorAccountOptional.get();

            //автоувеличение средств на счете
            mAccountService.checkFunds(String.valueOf(debtorAccount.getId()), payment.getPaymentConsent().getCurrency());

            if (debtorAccount.getBalanceAmount().compareTo(payment.getPaymentConsent().getAmount()) >= 0
                    //Проверка на то, что счет по дебету, не равен счету по кредиту.
                    && !debtorAccount.getIdentification().equals(creditorAccount.getIdentification())
                    //Проверка на то, что совпадают валюты перевода по счетам.
                    && !debtorAccount.getCurrency().equals(creditorAccount.getCurrency())) {

                transferAmount(debtorAccount, creditorAccount, payment);
                createTransactions(debtorAccount, creditorAccount, payment);

                payment.setStatus(ACCC);
            } else {
                payment.setStatus(RJCT);
            }
        } else {
            payment.setStatus(RJCT);
        }

        payment.setStatusUpdateTime(now);
        mPaymentRepository.save(payment);
    }

    private void transferAmount(
            final AccountEntity debtorAccount,
            final AccountEntity creditorAccount,
            final PaymentEntity payment
    ) {
        debtorAccount.setBalanceAmount(debtorAccount.getBalanceAmount().subtract(payment.getPaymentConsent().getAmount()));
        mAccountRepository.save(debtorAccount);

        creditorAccount.setBalanceAmount(creditorAccount.getBalanceAmount().add(payment.getPaymentConsent().getAmount()));
        mAccountRepository.save(creditorAccount);
    }

    private void createTransactions(
            final AccountEntity debtorAccount,
            final AccountEntity creditorAccount,
            final PaymentEntity payment
    ) {
        createDebtorTransaction(debtorAccount, payment);
        if (mBankRepository.existsByIdentifier(payment.getPaymentConsent().getCreditorAgentId())) {
            createCreditorTransaction(creditorAccount, payment);
        }
    }

    private void createDebtorTransaction(
            final AccountEntity debtorAccount,
            final PaymentEntity payment
    ) {
        createTransaction(OBCreditDebitCode1.DEBIT, debtorAccount, payment);
    }

    private void createCreditorTransaction(
            final AccountEntity creditorAccount,
            final PaymentEntity payment
    ) {
        createTransaction(OBCreditDebitCode1.CREDIT, creditorAccount, payment);
    }

    private void createTransaction(
            final OBCreditDebitCode1 creditDebitCode,
            final AccountEntity account,
            final PaymentEntity payment
    ) {
        final TransactionEntity debtorTransaction = new TransactionEntity();
        debtorTransaction.setAccount(account);
        debtorTransaction.setCreditDebitIndicator(creditDebitCode);
        debtorTransaction.setAmount(payment.getPaymentConsent().getAmount());
        debtorTransaction.setCurrency(payment.getPaymentConsent().getCurrency());
        debtorTransaction.setDebitAccIdentification(payment.getPaymentConsent().getDebtorAccId());
        debtorTransaction.setDebitBankIdentification(payment.getPaymentConsent().getDebtorAgentId());
        debtorTransaction.setDebitBankName(payment.getPaymentConsent().getDebtorAgentName());
        debtorTransaction.setDebitName(payment.getPaymentConsent().getDebtorName());
        debtorTransaction.setDebitTaxIdentification(payment.getPaymentConsent().getDebtorTaxId());
        debtorTransaction.setCreditAccIdentification(payment.getPaymentConsent().getCreditorAccId());
        debtorTransaction.setCreditBankIdentification(payment.getPaymentConsent().getCreditorAgentId());
        debtorTransaction.setCreditBankName(payment.getPaymentConsent().getCreditorAgentName());
        debtorTransaction.setCreditName(payment.getPaymentConsent().getCreditorName());
        debtorTransaction.setCreditTaxIdentification(payment.getPaymentConsent().getCreditorTaxId());
        debtorTransaction.setNumber(payment.getPaymentConsent().getId().toString());
        debtorTransaction.setBookingTime(new Date());
        mTransactionRepository.save(debtorTransaction);
    }
}
