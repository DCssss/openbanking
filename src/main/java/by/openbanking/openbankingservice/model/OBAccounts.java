package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OBAccounts")
public class OBAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accountId")
    private long accountId;

    @Column(name = "clientId")
    private long clientId;

    @Column(name = "accountStatus")
    private String accountStatus;

    @Column(name = "accountStatusUpdateTime")
    private Date accountStatusUpdateTime;

    @Column(name = "accountBalanceAmount")
    private double accountBalanceAmount;

    @Column(name = "accountCurrency")
    private String accountCurrency;

    @Column(name = "accountCreationTime")
    private Date accountCreationTime;

    @Column(name = "accountDescription")
    private String accountDescription;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "accountSubType")
    private String accountSubType;

    @Column(name = "accountIdentification")
    private String accountIdentification;

    public OBAccounts() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getAccountStatusUpdateTime() {
        return accountStatusUpdateTime;
    }

    public void setAccountStatusUpdateTime(Date accountStatusUpdateTime) {
        this.accountStatusUpdateTime = accountStatusUpdateTime;
    }

    public double getAccountBalanceAmount() {
        return accountBalanceAmount;
    }

    public void setAccountBalanceAmount(double accountBalanceAmount) {
        this.accountBalanceAmount = accountBalanceAmount;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public Date getAccountCreationTime() {
        return accountCreationTime;
    }

    public void setAccountCreationTime(Date accountCreationTime) {
        this.accountCreationTime = accountCreationTime;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getAccountIdentification() {
        return accountIdentification;
    }

    public void setAccountIdentification(String accountIdentification) {
        this.accountIdentification = accountIdentification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OBAccounts)) return false;
        OBAccounts OBAccounts = (OBAccounts) o;
        return getAccountId() == OBAccounts.getAccountId() && getClientId() == OBAccounts.getClientId() && Double.compare(OBAccounts.getAccountBalanceAmount(), getAccountBalanceAmount()) == 0 && Objects.equals(getAccountStatus(), OBAccounts.getAccountStatus()) && Objects.equals(getAccountStatusUpdateTime(), OBAccounts.getAccountStatusUpdateTime()) && Objects.equals(getAccountCurrency(), OBAccounts.getAccountCurrency()) && Objects.equals(getAccountCreationTime(), OBAccounts.getAccountCreationTime()) && Objects.equals(getAccountDescription(), OBAccounts.getAccountDescription()) && Objects.equals(getAccountName(), OBAccounts.getAccountName()) && Objects.equals(getAccountType(), OBAccounts.getAccountType()) && Objects.equals(getAccountSubType(), OBAccounts.getAccountSubType()) && getAccountIdentification().equals(OBAccounts.getAccountIdentification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getClientId(), getAccountStatus(), getAccountStatusUpdateTime(), getAccountBalanceAmount(), getAccountCurrency(), getAccountCreationTime(), getAccountDescription(), getAccountName(), getAccountType(), getAccountSubType(), getAccountIdentification());
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "accountId=" + accountId +
                ", clientId=" + clientId +
                ", accountStatus='" + accountStatus + '\'' +
                ", accountStatusUpdateTime=" + accountStatusUpdateTime +
                ", accountBalanceAmount=" + accountBalanceAmount +
                ", accountCurrency='" + accountCurrency + '\'' +
                ", accountCreationTime=" + accountCreationTime +
                ", accountDescription='" + accountDescription + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountSubType='" + accountSubType + '\'' +
                ", accountIdentification='" + accountIdentification + '\'' +
                '}';
    }
}



