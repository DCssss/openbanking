package by.openbanking.openbankingservice.model;

import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Data;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1PostData;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.threeten.bp.ZoneId;

import javax.persistence.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "OB_ACCOUNT_CONSENTS")
public final class AccountConsents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long accountConsentId;

    @Column(name = "STATUS")
    private String accountConsentStatus;

    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "TRANSACTION_FROM_DATE")
    private Date transactionFromDate;

    @Column(name = "TRANSACTION_TO_DATE")
    private Date transactionToDate;

    @Column(name = "STATUS_UPDATE_TIME")
    private Date statusUpdateTime;

    @Column(name = "FINTECH_ID")
    private Long fintechId;

    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Column(name = "READ_ACCOUNTS_BASIC")
    private int readAccountsBasic;

    @Column(name = "READ_ACCOUNTS_DETAIL")
    private int readAccountsDetail;

    @Column(name = "READ_BALANCES")
    private int readBalances;

    @Column(name = "READ_STATEMENTS_BASIC")
    private int readStatementsBasic;

    @Column(name = "READ_STATEMENTS_DETAIL")
    private int readStatementsDetail;

    @Column(name = "READ_TRANSACTION_BASIC")
    private int readTransactionsBasic;

    @Column(name = "READ_TRANSACTION_DETAIL")
    private int readTransactionsDetail;

    @Column(name = "READ_TRANSACTION_CREDITS")
    private int readTransactionsCredits;

    @Column(name = "READ_TRANSACTION_DEBITS")
    private int readTransactionsDebits;

    public static AccountConsents valueOf(OBReadConsent1Data model) {
        AccountConsents accountConsents = new AccountConsents();
        for (OBReadConsent1Data.PermissionsEnum permissionsEnum : model.getPermissions()) {
            switch (permissionsEnum) {
                case READACCOUNTSBASIC:
                    accountConsents.readAccountsBasic = 1;
                    break;
                case READACCOUNTSDETAIL:
                    accountConsents.readAccountsDetail = 1;
                    break;
                case READBALANCES:
                    accountConsents.readBalances = 1;
                    break;
                case READSTATEMENTSDETAIL:
                    accountConsents.readStatementsDetail = 1;
                    break;
                case READSTATEMENTSBASIC:
                    accountConsents.readStatementsBasic = 1;
                    break;
                case READTRANSACTIONSBASIC:
                    accountConsents.readTransactionsBasic = 1;
                    break;
                case READTRANSACTIONSDETAIL:
                    accountConsents.readTransactionsDetail = 1;
                    break;
                case READTRANSACTIONSCREDITS:
                    accountConsents.readTransactionsCredits = 1;
                    break;
                case READTRANSACTIONSDEBITS:
                    accountConsents.readTransactionsDebits = 1;
                    break;
            }
        }
        accountConsents.expirationDate = new Date(model.getExpirationDate().getTime());
        accountConsents.transactionFromDate = new Date(model.getTransactionFromDate().getTime());
        accountConsents.transactionToDate = new Date(model.getTransactionToDate().getTime());
        return accountConsents;
    }

    public Long getAccountConsentId() {
        return accountConsentId;
    }

    public String getAccountConsentStatus() {
        return accountConsentStatus;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Date getTransactionFromDate() {
        return transactionFromDate;
    }

    public Date getTransactionToDate() {
        return transactionToDate;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public Long getFintechId() {
        return fintechId;
    }

    public Long getClientId() {
        return clientId;
    }

    public int getReadAccountsBasic() {
        return readAccountsBasic;
    }

    public int getReadAccountsDetail() {
        return readAccountsDetail;
    }

    public int getReadBalances() {
        return readBalances;
    }

    public int getReadStatementsBasic() {
        return readStatementsBasic;
    }

    public int getReadStatementsDetail() {
        return readStatementsDetail;
    }

    public int getReadTransactionsBasic() {
        return readTransactionsBasic;
    }

    public int getReadTransactionsDetail() {
        return readTransactionsDetail;
    }

    public int getReadTransactionsCredits() {
        return readTransactionsCredits;
    }

    public int getReadTransactionsDebits() {
        return readTransactionsDebits;
    }

    public void setAccountConsentStatus(final String accountConsentStatus) {
        this.accountConsentStatus = accountConsentStatus;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setStatusUpdateTime(final Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public OBReadConsentResponse1Data toOBReadConsentResponse1Data() {
        final OBReadConsentResponse1Data responseData = new OBReadConsentResponse1Data();
        responseData.setAccountConsentId(accountConsentId.toString());
        responseData.setStatus(OBReadConsentResponse1Data.StatusEnum.fromValue(accountConsentStatus));
        responseData.setCreationDateTime(creationTime);
        responseData.setStatusUpdateDateTime(statusUpdateTime);
        responseData.setExpirationDate(expirationDate);
        responseData.setTransactionFromDate(transactionFromDate);
        responseData.setTransactionToDate(transactionToDate);
        final List<OBReadConsentResponse1Data.PermissionsEnum> permissions = new ArrayList<>();
        if (readAccountsBasic == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READACCOUNTSBASIC);
        }
        if (readAccountsDetail == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READACCOUNTSDETAIL);
        }
        if (readBalances == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READBALANCES);
        }
        if (readStatementsBasic == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READSTATEMENTSBASIC);
        }
        if (readStatementsDetail == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READSTATEMENTSDETAIL);
        }
        if (readTransactionsBasic == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READTRANSACTIONSBASIC);
        }
        if (readTransactionsDetail == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READTRANSACTIONSDETAIL);
        }
        if (readTransactionsCredits == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READTRANSACTIONSCREDITS);
        }
        if (readTransactionsDebits == 1) {
            permissions.add(OBReadConsentResponse1Data.PermissionsEnum.READTRANSACTIONSDEBITS);
        }
        responseData.setPermissions(permissions);
        return responseData;
    }

    public OBReadConsentResponse1PostData toOBReadConsentResponsePost1Data() {
        final OBReadConsentResponse1PostData responseData = new OBReadConsentResponse1PostData();
        responseData.setAccountConsentId(accountConsentId.toString());
        responseData.setStatus(OBReadConsentResponse1PostData.StatusEnum.fromValue(accountConsentStatus));
        responseData.setCreationDateTime(creationTime);
        responseData.setStatusUpdateDateTime(statusUpdateTime);
        responseData.setExpirationDate(expirationDate);
        responseData.setTransactionFromDate(transactionFromDate);
        responseData.setTransactionToDate(transactionToDate);
        final List<OBReadConsentResponse1PostData.PermissionsEnum> permissions = new ArrayList<>();
        if (readAccountsBasic == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READACCOUNTSBASIC);
        }
        if (readAccountsDetail == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READACCOUNTSDETAIL);
        }
        if (readBalances == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READBALANCES);
        }
        if (readStatementsBasic == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READSTATEMENTSBASIC);
        }
        if (readStatementsDetail == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READSTATEMENTSDETAIL);
        }
        if (readTransactionsBasic == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READTRANSACTIONSBASIC);
        }
        if (readTransactionsDetail == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READTRANSACTIONSDETAIL);
        }
        if (readTransactionsCredits == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READTRANSACTIONSCREDITS);
        }
        if (readTransactionsDebits == 1) {
            permissions.add(OBReadConsentResponse1PostData.PermissionsEnum.READTRANSACTIONSDEBITS);
        }
        responseData.setPermissions(permissions);
        return responseData;
    }
}