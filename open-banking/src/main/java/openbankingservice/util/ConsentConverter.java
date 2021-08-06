package openbankingservice.util;

import openbankingservice.data.entity.ConsentEntity;
import openbankingservice.models.accinfo.AccountConsentsStatus;
import openbankingservice.models.accinfo.ConsentData;
import openbankingservice.models.accinfo.ConsentResponseData;
import openbankingservice.models.accinfo.Permission;

import java.util.ArrayList;
import java.util.Date;

public final class ConsentConverter {

    private ConsentConverter() {
    }
    //Заглушка для сценария когда после создания согласия, мы должны отобразить статус AwaitingAuthorization, но в базу сохраняем рандомный сценарий.
    public static ConsentResponseData toConsentResponseDataForPost(final ConsentEntity consentEntity) {
        return new ConsentResponseData()
                .link("https://sdbo_business.bank.by/accountConsentsId/" + consentEntity.getId() + "/")
                .accountConsentId(String.valueOf(consentEntity.getId()))
                .status(AccountConsentsStatus.AWAITINGAUTHORISATION)
                .creationDateTime(consentEntity.getCreationTime())
                .statusUpdateDateTime(consentEntity.getStatusUpdateTime())
                .expirationDate(consentEntity.getExpirationDate())
                .transactionFromDate(consentEntity.getTransactionFromDate())
                .transactionToDate(consentEntity.getTransactionToDate())
                .permissions(new ArrayList<>(consentEntity.getPermission()));
    }

    public static ConsentResponseData toConsentResponseDataForGet(final ConsentEntity consentEntity) {
        return new ConsentResponseData()
                .link("https://sdbo_business.bank.by/accountConsentsId/" + consentEntity.getId() + "/")
                .accountConsentId(String.valueOf(consentEntity.getId()))
                .status(consentEntity.getStatus())
                .creationDateTime(consentEntity.getCreationTime())
                .statusUpdateDateTime(consentEntity.getStatusUpdateTime())
                .expirationDate(consentEntity.getExpirationDate())
                .transactionFromDate(consentEntity.getTransactionFromDate())
                .transactionToDate(consentEntity.getTransactionToDate())
                .permissions(new ArrayList<>(consentEntity.getPermission()));
    }

    public static ConsentEntity toConsentEntity(final ConsentData model) {
        ConsentEntity consent = new ConsentEntity();
        for (Permission permissionEnum : model.getPermissions()) {
            switch (permissionEnum) {
                case READACCOUNTSBASIC:
                    consent.setReadAccountsBasic(1);
                    break;
                case READACCOUNTSDETAIL:
                    consent.setReadAccountsDetail(1);
                    break;
                case READBALANCES:
                    consent.setReadBalances(1);
                    break;
                case READSTATEMENTSDETAIL:
                    consent.setReadStatementsDetail(1);
                    break;
                case READSTATEMENTSBASIC:
                    consent.setReadStatementsBasic(1);
                    break;
                case READTRANSACTIONSBASIC:
                    consent.setReadTransactionsBasic(1);
                    break;
                case READTRANSACTIONSDETAIL:
                    consent.setReadTransactionsDetail(1);
                    break;
                case READTRANSACTIONSCREDITS:
                    consent.setReadTransactionsCredits(1);
                    break;
                case READTRANSACTIONSDEBITS:
                    consent.setReadTransactionsDebits(1);
                    break;
            }
        }
        consent.setExpirationDate(new Date(model.getExpirationDate().getTime()));
        consent.setTransactionFromDate(new Date(model.getTransactionFromDate().getTime()));
        consent.setTransactionToDate(new Date(model.getTransactionToDate().getTime()));
        return consent;
    }
}
