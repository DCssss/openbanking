package openbankingservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.exception.OBException;
import openbankingservice.models.payments.*;

import java.math.BigDecimal;
import java.util.Date;

import static openbankingservice.exception.OBErrorCode.BY_NBRB_UNEXPECTED_ERROR;

public final class PaymentConsentConverter {

    private static final String LINK_PATTERN = "https://sdbo.bank.by/domesticTaxConsentId/%s/";

    private PaymentConsentConverter() {
    }

    public static PaymentConsentEntity toPaymentConsentEntity(final OBDataTaxDomestic data) {
        final Date now = new Date();
        final ObjectMapper objectMapper = new ObjectMapper();
        String initiationBlock;
        try {
            initiationBlock = objectMapper.writeValueAsString(data.getInitiation());
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }
        final PaymentConsentEntity paymentConsentEntity = new PaymentConsentEntity();
        paymentConsentEntity.setCreationTime(now);
        paymentConsentEntity.setStatus(StatusPaymentConsent.AWAITINGAUTHORISATION);
        paymentConsentEntity.setType(TypePaymentConsent.DOMESTICTAXCONSENT);
        paymentConsentEntity.setInitiation(initiationBlock);
        paymentConsentEntity.setAuthorizationType(data.getAuthorisation().getAuthorizationType());
        paymentConsentEntity.setStatusUpdateTime(now);
        paymentConsentEntity.setInstructionId(data.getInitiation().getInstructionIdentification());
        paymentConsentEntity.setEndToEndId(data.getInitiation().getEndToEndIdentification());
        paymentConsentEntity.setLocalInstrument(data.getInitiation().getLocalInstrument());
        paymentConsentEntity.setAmount(BigDecimal.valueOf(Double.parseDouble(data.getInitiation().getAmount())));
        paymentConsentEntity.setCurrency(data.getInitiation().getCurrency());
        paymentConsentEntity.setRequestedExecutionDate(data.getInitiation().getRequestedExecutionDate());
        paymentConsentEntity.setDebtorTaxId(data.getInitiation().getDebtor().getTaxIdentification());
        paymentConsentEntity.setDebtorName(data.getInitiation().getDebtor().getName());
        paymentConsentEntity.setDebtorAccId(data.getInitiation().getDebtor().getDebtorAccount().getIdentification());
        paymentConsentEntity.setDebtorAccScheme(data.getInitiation().getDebtor().getDebtorAccount().getSchemeName());
        paymentConsentEntity.setDebtorAgentId(data.getInitiation().getDebtor().getDebtorAgent().getIdentification());
        paymentConsentEntity.setDebtorAgentName(data.getInitiation().getDebtor().getDebtorAgent().getName());
        paymentConsentEntity.setCreditorTaxId(data.getInitiation().getCreditor().getTaxIdentification());
        paymentConsentEntity.setCreditorName(data.getInitiation().getCreditor().getName());
        paymentConsentEntity.setCreditorAccId(data.getInitiation().getCreditor().getCreditorAccount().getIdentification());
        paymentConsentEntity.setCreditorAccScheme(data.getInitiation().getCreditor().getCreditorAccount().getSchemeName());
        paymentConsentEntity.setCreditorAgentId(data.getInitiation().getCreditor().getCreditorAgent().getIdentification());
        paymentConsentEntity.setCreditorAgentName(data.getInitiation().getCreditor().getCreditorAgent().getName());
        paymentConsentEntity.setConsentLink(String.format(LINK_PATTERN, paymentConsentEntity.getId()));

        return paymentConsentEntity;
    }

    public static OBDataTax toOBDataTax(final PaymentConsentEntity paymentConsentEntity) {
        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        return new OBDataTax()
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .link(String.format(LINK_PATTERN, paymentConsentEntity.getId()))
                .status(PaymentConsentStatus.valueOf(paymentConsentEntity.getStatus().toString()))
                .statusUpdateDateTime(paymentConsentEntity.getStatusUpdateTime())
                .creationDateTime(paymentConsentEntity.getCreationTime())
                .cutOffDateTime(new Date())
                .expectedExecutionDate(paymentConsentEntity.getRequestedExecutionDate())
                .expectedSettlementDate(paymentConsentEntity.getRequestedExecutionDate())
                .initiation(initiation);
    }

    public static OBDataTax1 toOBDataTax1(final PaymentConsentEntity paymentConsentEntity) {
        final ObjectMapper objectMapper = new ObjectMapper();
        OBInitiationTaxDomestic initiation;
        try {
            initiation = objectMapper.readValue(paymentConsentEntity.getInitiation(), OBInitiationTaxDomestic.class);
        } catch (JsonProcessingException e) {
            throw new OBException(BY_NBRB_UNEXPECTED_ERROR, "");
        }

        return new OBDataTax1()
                .domesticTaxConsentId(paymentConsentEntity.getId().toString())
                .link(String.format(LINK_PATTERN, paymentConsentEntity.getId()))
                .status(PaymentConsentStatus.valueOf(paymentConsentEntity.getStatus().toString()))
                .statusUpdateDateTime(paymentConsentEntity.getStatusUpdateTime())
                .creationDateTime(paymentConsentEntity.getCreationTime())
                .cutOffDateTime(new Date())
                .expectedExecutionDate(paymentConsentEntity.getRequestedExecutionDate())
                .expectedSettlementDate(paymentConsentEntity.getRequestedExecutionDate())
                .initiation(initiation);
    }

}
