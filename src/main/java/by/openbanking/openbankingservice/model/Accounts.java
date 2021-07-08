package by.openbanking.openbankingservice.model;

import by.openbanking.openbankingservice.models.Account;
import by.openbanking.openbankingservice.models.AccountDetails;
import by.openbanking.openbankingservice.models.InlineResponse200;
import by.openbanking.openbankingservice.models.Servicer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "OB_ACCOUNTS")
public class Accounts extends by.openbanking.openbankingservice.models.InlineResponse200 {

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

    public Accounts() {
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
        if (!(o instanceof Accounts)) return false;
        Accounts Accounts = (Accounts) o;
        return getAccountId() == Accounts.getAccountId() && getClientId() == Accounts.getClientId() && Double.compare(Accounts.getAccountBalanceAmount(), getAccountBalanceAmount()) == 0 && Objects.equals(getAccountStatus(), Accounts.getAccountStatus()) && Objects.equals(getAccountStatusUpdateTime(), Accounts.getAccountStatusUpdateTime()) && Objects.equals(getAccountCurrency(), Accounts.getAccountCurrency()) && Objects.equals(getAccountCreationTime(), Accounts.getAccountCreationTime()) && Objects.equals(getAccountDescription(), Accounts.getAccountDescription()) && Objects.equals(getAccountName(), Accounts.getAccountName()) && Objects.equals(getAccountType(), Accounts.getAccountType()) && Objects.equals(getAccountSubType(), Accounts.getAccountSubType()) && getAccountIdentification().equals(Accounts.getAccountIdentification());
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

    public static final class Data extends InlineResponse200 {

    }

    public static final class Links extends by.openbanking.openbankingservice.models.Links {

    }

    public static final class Link extends by.openbanking.openbankingservice.models.Link {

    }

    public static final class Meta extends by.openbanking.openbankingservice.models.Meta {

    }


    public List<Account> toAccount()  {

        Account acc = new Account();
        Servicer servicer = new Servicer();
        AccountDetails accountDetails = new AccountDetails();
        acc.setAccountid(String.valueOf(accountId));
        acc.setStatus(Account.StatusEnum.PENDING);
        acc.setStatusUpdateTime(accountStatusUpdateTime);
        acc.setCurrency(accountCurrency);
        acc.setAccountType(accountType);
        acc.setAccountSubType(Account.AccountSubTypeEnum.LOAN);
        acc.setCreationDataTime(accountCreationTime);
        acc.setAccountDescription(accountDescription);
        servicer.setIdentification("ЗАО БСБ Банк");
        servicer.setName("UNBSBY2X");
        accountDetails.setIdentification(accountIdentification);
        accountDetails.setSubstatus("Arrested");
        accountDetails.setName(accountName);
        accountDetails.setReason("Арестован согласно Постановления СК");
        accountDetails.setSchemeName("Схема для осуществления платежа по номеру счета");
        acc.setAccountDetails(accountDetails);
        acc.setServicer(servicer);

        final List<Account> accT = new ArrayList<>();
        accT.add(acc);

        return accT;
    }

}



