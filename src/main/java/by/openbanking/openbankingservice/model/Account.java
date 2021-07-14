package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OB_ACCOUNTS")
public final class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ACCOUNT_ID")
    private long accountId;

    @Column(name = "CLIENT_ID")
    private long clientId;

    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    @Column(name = "ACCOUNT_STATUS_UPDATE_TIME")
    private Date accountStatusUpdateTime;

    @Column(name = "ACCOUNT_BALANCE_AMOUNT")
    private double accountBalanceAmount;

    @Column(name = "ACCOUNT_CURRENCY")
    private String accountCurrency;

    @Column(name = "ACCOUNT_CREATION_TIME")
    private Date accountCreationTime;

    @Column(name = "ACCOUNT_DESCRIPTION")
    private String accountDescription;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "ACCOUNT_SUB_TYPE")
    private String accountSubType;

    @Column(name = "ACCOUNT_IDENTIFICATION")
    private String accountIdentification;

    public Account() {
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
        if (!(o instanceof Account)) return false;
        Account Account = (Account) o;
        return getAccountId() == Account.getAccountId() && getClientId() == Account.getClientId() && Double.compare(Account.getAccountBalanceAmount(), getAccountBalanceAmount()) == 0 && Objects.equals(getAccountStatus(), Account.getAccountStatus()) && Objects.equals(getAccountStatusUpdateTime(), Account.getAccountStatusUpdateTime()) && Objects.equals(getAccountCurrency(), Account.getAccountCurrency()) && Objects.equals(getAccountCreationTime(), Account.getAccountCreationTime()) && Objects.equals(getAccountDescription(), Account.getAccountDescription()) && Objects.equals(getAccountName(), Account.getAccountName()) && Objects.equals(getAccountType(), Account.getAccountType()) && Objects.equals(getAccountSubType(), Account.getAccountSubType()) && getAccountIdentification().equals(Account.getAccountIdentification());
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


