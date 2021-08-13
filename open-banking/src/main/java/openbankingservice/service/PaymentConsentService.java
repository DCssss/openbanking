package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.ClientEntity;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.data.repository.PaymentConsentRepository;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.models.payments.*;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.PaymentConsentConverter;
import openbankingservice.util.StubData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static openbankingservice.exception.OBErrorCode.BY_NBRB_FIELD_INVALID_DATE;

@Service
@RequiredArgsConstructor
public class PaymentConsentService {

    private final PaymentConsentRepository mPaymentConsentRepository;

    private final FintechService mFintechService;
    private final ClientService mClientService;

    public ResponseEntity<OBPaymentConsentTax1> createDomesticTaxConsents(
            final OBPaymentConsentTax body,
            final String xIdempotencyKey,
            final String xJwsSignature,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
             @RequestHeader(value = "authorization", required = true)  String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());

        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataTax data = PaymentConsentConverter.toOBDataTax(paymentConsentEntity)
                .authorisation(body.getData().getAuthorisation());


        //Почему-то иногда не приходит заголовок с авторизацией от ВСО, либо он его обрезает. На всякий случай привяжем первый финтех.
        if (StringUtils.isBlank(authorization) || authorization.equals("Bearer null") || authorization.equals("Bearer ") ) {
            authorization = StubData.FINTECHS.keySet().stream().findFirst().get();
        }


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
            final String xCustomerUserAgent,
            final String xApiKey
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
             String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());

        if (StringUtils.isBlank(authorization) || authorization.equals("Bearer null") || authorization.equals("Bearer ") ) {
            authorization = StubData.FINTECHS.keySet().stream().findFirst().get();
        }
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
            final String xCustomerUserAgent,
            final String xApiKey
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
            final String xCustomerUserAgent,
            final String xApiKey
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

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<OBPaymentConsentListAccounts2> getPaymentsConsentsOfListAccountsByListAccountsConsentId(
            final String listAccountsConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
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
            final String xCustomerUserAgent,
            final String xApiKey
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

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<OBPaymentConsentListPassports2> getPaymentConsentsOfListPassportsByListPassportsConsentId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
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
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataReqResp1 data = PaymentConsentConverter.toOBDataReqResp1(paymentConsentEntity);

        final OBReqConsent2 response = new OBReqConsent2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<OBVRPConsent2> getPaymentConsentsVPRByVRPConsentId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataVRPResp1 data = PaymentConsentConverter.toOBDataVRPResp1(paymentConsentEntity);

        final OBVRPConsent2 response = new OBVRPConsent2()
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
            final String xCustomerUserAgent,
            final String xApiKey
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

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

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
        final PaymentConsentEntity paymentConsentEntity = PaymentConsentConverter.toPaymentConsentEntity(body.getData());
        paymentConsentEntity.setFintech(mFintechService.identifyFintech(authorization));

        mPaymentConsentRepository.save(paymentConsentEntity);

        final OBDataVRPResp data = PaymentConsentConverter.toOBDataVRPResp(paymentConsentEntity);

        final OBVRPConsent1 response = new OBVRPConsent1()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<OBPaymentConsentTaxReq2> getPaymentConsentsTaxRequirementByTaxRequirementConsentId(
            final String paymentConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(paymentConsentId));

        final OBDataTaxReq2 data = PaymentConsentConverter.toOBDataTaxReq2(paymentConsentEntity);

        final OBPaymentConsentTaxReq2 response = new OBPaymentConsentTaxReq2()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePaymentConsentsVPRByVPRConsentId(
            final String vrPConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xCustomerUserAgent,
            final String xApiKey
    ) {
        final PaymentConsentEntity paymentConsentEntity = mPaymentConsentRepository.getById(Long.valueOf(vrPConsentId));

        paymentConsentEntity.setStatus(StatusPaymentConsent.REVOKED);
        paymentConsentEntity.setStatusUpdateTime(new Date());

        mPaymentConsentRepository.save(paymentConsentEntity);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<OBPaymentsConsentsList> getListOfPaymentsConsents(
            @NotNull @Valid final Date fromCreationDate,
            @NotNull @Valid final Date toCreationDate,
            @Valid final String type,
            @Valid final String status,
            final String xApiKey
    ) {
        if (fromCreationDate.after(toCreationDate)) {
            throw new OBException(BY_NBRB_FIELD_INVALID_DATE, "fromCreationDate must be before toCreationDate", "fromCreationDate");
        }

        final List<PaymentConsentEntity> paymentConsents = mPaymentConsentRepository.findAllByCreationTimeBetweenAndTypeAndStatus(fromCreationDate, toCreationDate, TypePaymentConsent.fromValue(type), StatusPaymentConsent.fromValue(status));

        final OBPaymentConsent obPaymentConsent = new OBPaymentConsent();
        obPaymentConsent.addAll(paymentConsents);

        final OBDataPaymentsConsentsList data = new OBDataPaymentsConsentsList()
                .paymentConsent(obPaymentConsent);

        final OBPaymentsConsentsList response = new OBPaymentsConsentsList()
                .data(data);

        final HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional
    public void authorizePaymentConsent(
            final String xApiKey,
            final String xPaymentConsentId
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity consent = mPaymentConsentRepository.getById(Long.valueOf(xPaymentConsentId));

        if (consent.getStatus() == StatusPaymentConsent.AWAITINGAUTHORISATION) {

            consent.setClient(client);
            consent.setStatus(StatusPaymentConsent.AUTHORISED);
            consent.setStatusUpdateTime(new Date());
            mPaymentConsentRepository.save(consent);

        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Invalid payment consent status");
        }
    }

    @Transactional
    public void rejectConsent(
            final String xApiKey,
            final String xPaymentConsentId
    ) {
        final ClientEntity client = mClientService.identifyClient(xApiKey);
        final PaymentConsentEntity consent = mPaymentConsentRepository.getById(Long.valueOf(xPaymentConsentId));

        if (consent.getStatus() == StatusPaymentConsent.AWAITINGAUTHORISATION) {

            consent.setClient(client);
            consent.setStatus(StatusPaymentConsent.REJECTED);
            consent.setStatusUpdateTime(new Date());
            mPaymentConsentRepository.save(consent);

        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS, "Invalid payment consent status");
        }
    }
}
