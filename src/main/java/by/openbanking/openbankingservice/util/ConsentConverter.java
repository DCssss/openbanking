package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.ConsentEntity;
import by.openbanking.openbankingservice.models.ConsentResponseData;

import java.util.ArrayList;

public final class ConsentConverter {

    private ConsentConverter() {
    }

    public static ConsentResponseData toConsentResponseData(final ConsentEntity consentEntity) {
        final ConsentResponseData responseData = new ConsentResponseData();
        responseData.setLink("https://sdbo_business.bank.by/accountConsentsId/" + consentEntity.getId() + "/");
        responseData.setAccountConsentId(String.valueOf(consentEntity.getId()));
        responseData.setStatus(consentEntity.getStatus());
        responseData.setCreationDateTime(consentEntity.getCreationTime());
        responseData.setStatusUpdateDateTime(consentEntity.getStatusUpdateTime());
        responseData.setExpirationDate(consentEntity.getExpirationDate());
        responseData.setTransactionFromDate(consentEntity.getTransactionFromDate());
        responseData.setTransactionToDate(consentEntity.getTransactionToDate());
        responseData.setPermissions(new ArrayList<>(consentEntity.getPermission()));
        return responseData;
    }
}
