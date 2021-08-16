package openbankingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.*;
import openbankingservice.data.repository.*;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.models.accinfo.OBCreditDebitCode1;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.*;

import static openbankingservice.data.entity.PaymentEntity.Status.ACCC;
import static openbankingservice.data.entity.PaymentEntity.Status.RJCT;
import static openbankingservice.exception.OBErrorCode.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Map<TypePaymentConsent, TypePayment> PAYMENT_CONSENT_TO_PAYMENT_TYPES = new HashMap<>();

    static {
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.DOMESTICCONSENT, TypePayment.DOMESTIC);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.DOMESTICTAXCONSENT, TypePayment.DOMESTICTAX);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.REQUIREMENTCONSENT, TypePayment.REQUIREMENT);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.TAXREQUIREMENTCONSENT, TypePayment.TAXREQUIREMENT);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.LISTACCOUNTSCONSENT, TypePayment.LISTACCOUNTS);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.LISTPASSPORTSCONSENT, TypePayment.LISTPASSPORTS);
        PAYMENT_CONSENT_TO_PAYMENT_TYPES.put(TypePaymentConsent.VRPCONSENT, TypePayment.VRP);
    }

    private final PaymentRepository mPaymentRepository;
    private final PaymentConsentRepository mPaymentConsentRepository;
    private final AccountRepository mAccountRepository;
    private final TransactionRepository mTransactionRepository;
    private final AccountService mAccountService;
    private final ClientService mClientService;
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.DOMESTICCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.DOMESTIC_CONSENT_ID, domesticConsentId);


        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticTaxConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.DOMESTICTAXCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.DOMESTIC_TAX_CONSENT_ID, domesticTaxConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {

        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListAccounts initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListAccounts.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.LISTACCOUNTSCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.LIST_ACCOUNT_CONSENT_ID,listAccountsConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationListPassports initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationListPassports.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.LISTPASSPORTSCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID, paymentConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<OBTaxPayment2> getDomesticTaxByDomesticTaxId(
            final String domesticTaxId,
            final String domesticTaxConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticTaxConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.DOMESTIC_TAX_CONSENT_ID,domesticTaxConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPayment2> getDomesticByDomesticId(
            final String domesticId,
            final String domesticConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(domesticConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.DOMESTIC_CONSENT_ID,domesticConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentListAccounts2> getPaymentsListAccountsByListAccountsId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID,paymentConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentListPassports2> getPaymentListPassports(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID,paymentConsentId);
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.REQUIREMENTCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.LIST_ACCOUNT_CONSENT_ID, listAccountsConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<OBPaymentReq2> getPaymentsRequirimentsByRequirementId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID, paymentConsentId);

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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxReq initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxReq.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.TAXREQUIREMENTCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.LIST_ACCOUNT_CONSENT_ID, listAccountsConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBTaxPaymentReq2> getPaymentsTaxRequirementsByTaxRequirementId(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID, paymentConsentId);

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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(listAccountsConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationVRP initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationVRP.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, e.getMessage());
        }

        if (!body.getData().getInitiation().equals(initiation)) {
            throw new OBException(BY_NBRB_FIELD_INVALID, "Consent and payment initiation block does not match", "Data.Initiation");
        }

        final PaymentEntity payment = checkConsentAndGetPayment(paymentConsentEntity, TypePaymentConsent.VRPCONSENT);

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                xIdempotencyKey,
                xJwsSignature,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.LIST_ACCOUNT_CONSENT_ID, listAccountsConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBVRP2> getPaymentsVRPbyVRPid(
            final String paymentId,
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        if (paymentConsentEntity.getClient().equals(client)){
            throw new OBException(BY_NBRB_FIELD_INVALID, "Invalid client");
        }

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

        final HttpHeaders headers = new OBHttpHeaders().getPaymentHeaders(
                null,
                null,
                xFapiAuthDate,
                xFapiInteractionId,
                authorization,
                xFapiCustomerIpAddress,
                xCustomerUserAgent,
                xApiKey
        );
        headers.add(OBHttpHeaders.PAYMENT_CONSENT_ID, paymentConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBPaymentsList> getListOfPayments(
            @NotNull @Valid final Date fromCreationDate,
            @NotNull @Valid final Date toCreationDate,
            @Valid final String type,
            @Valid final String status,
            final String xApiKey
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
        headers.add(OBHttpHeaders.X_API_KEY,xApiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional
    public void makePayment(final String paymentId) {

        final Date now = new Date();

        final PaymentEntity payment = mPaymentRepository.getById(Long.valueOf(paymentId));
        final Optional<AccountEntity> debtorAccountOptional = mAccountRepository.findByIdentification(payment.getPaymentConsent().getDebtorAccId());
        final AccountEntity creditorAccount = mAccountRepository.getByIdentification(payment.getPaymentConsent().getCreditorAccId());
        //Проверка на корректный IBAN по дебету и кредиту

        //Проверка на существование дебетового счета
        if (debtorAccountOptional.isPresent()) {

            final AccountEntity debtorAccount = debtorAccountOptional.get();
            //проверяем дебетовый счет на формат ИБАНА
            boolean debetIbanCheck = IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(debtorAccount.getIdentification());
            //автоувеличение средств на счете
            mAccountService.checkFunds(String.valueOf(debtorAccount.getId()), payment.getPaymentConsent().getCurrency());

            if (debtorAccount.getBalanceAmount().compareTo(payment.getPaymentConsent().getAmount()) >= 0
                    //Проверка на то, что счет по дебету, не равен счету по кредиту.
                    && !debtorAccount.getIdentification().equals(creditorAccount.getIdentification())
                    //Проверка на то, что совпадают валюты перевода по счетам.
                    && debtorAccount.getCurrency().equals(creditorAccount.getCurrency())
                    && debetIbanCheck) {

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

    private void checkPaymentConsent(
            final PaymentConsentEntity paymentConsentEntity,
            final TypePaymentConsent type
    ) {
        checkPaymentConsentStatus(paymentConsentEntity);
        checkPaymentConsentType(paymentConsentEntity, type);
    }

    private void checkPaymentConsentStatus(final PaymentConsentEntity paymentConsentEntity) {
        if (paymentConsentEntity.getStatus() != StatusPaymentConsent.AUTHORISED) {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_CONSENT_MISMATCH, "Invalid payment consent status");
        }
    }

    private void checkPaymentConsentType(
            final PaymentConsentEntity paymentConsentEntity,
            final TypePaymentConsent type
    ) {
        if (paymentConsentEntity.getType() != type) {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_CONSENT_MISMATCH, "Invalid payment type");
        }
    }

    private PaymentEntity createAndGetPayment(
            final PaymentConsentEntity paymentConsent
    ) {
        final PaymentEntity payment = createPayment(paymentConsent);
        consumePaymentConsent(paymentConsent);
        return payment;
    }

    private void consumePaymentConsent(
            final PaymentConsentEntity paymentConsent
    ) {
        paymentConsent.setStatus(StatusPaymentConsent.CONSUMED);
        paymentConsent.setStatusUpdateTime(new Date());
        mPaymentConsentRepository.save(paymentConsent);
    }

    private PaymentEntity createPayment(
            final PaymentConsentEntity paymentConsent
    ) {
        final Date now = new Date();

        final PaymentEntity payment = new PaymentEntity();
        payment.setPaymentConsent(paymentConsent);
        payment.setType(PAYMENT_CONSENT_TO_PAYMENT_TYPES.get(paymentConsent.getType()));
        payment.setStatus(PaymentEntity.Status.PDNG);
        payment.setCreateTime(now);
        payment.setStatusUpdateTime(now);

        mPaymentRepository.save(payment);

        return payment;
    }

    private PaymentEntity checkConsentAndGetPayment(
            final PaymentConsentEntity paymentConsentEntity,
            final TypePaymentConsent type
    ) {
        checkPaymentConsent(paymentConsentEntity, type);
        return createAndGetPayment(paymentConsentEntity);
    }


}
