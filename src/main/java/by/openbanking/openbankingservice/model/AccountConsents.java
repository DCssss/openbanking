package by.openbanking.openbankingservice.model;

import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "accountConsents")
public class AccountConsents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "accountConsentId")
    private String accountConsentId;

    @Column(name = "status")
    private String status;

    @Column(name = "creationDateTime")
    private SimpleDateFormat creationDateTime;

    @Column(name = "expirationDate")
    private Date expirationDate;

    @Column(name = "transactionFromDate")
    private Date transactionFromDate;

    @Column(name = "transactionToDate")
    private Date  transactionToDate;

    @Column(name = "statusUpdateDateTime")
    private SimpleDateFormat statusUpdateDateTime;

    @Column(name = "finTechId")
    private int finTechId;

    @Column(name = "clientId")
    private int clientId;

    @Column(name = "apiKey")
    private String apiKey;

    @Column(name = "readAccountsBasic")
    private int readAccountsBasic;

    @Column(name = "readAccountsDetail")
    private int readAccountsDetail;

    @Column(name = "readBalances")
    private int readBalances;

    @Column(name = "readStatementsDetail")
    private int readStatementsDetail;

    @Column(name = "readStatementsBasic")
    private int readStatementsBasic;

    @Column(name = "readTransactionsDetail")
    private int readTransactionsDetail;

    @Column(name = "readTransactionsBasic")
    private int readTransactionsBasic;

    @Column(name = "readTransactionsCredits")
    private int readTransactionsCredits;

    @Column(name = "readTransactionsDebits")
    private int readTransactionsDebits;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountConsentId() {
        return accountConsentId;
    }

    public void setAccountConsentId(String accountConsentId) {
        this.accountConsentId = accountConsentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SimpleDateFormat getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(SimpleDateFormat creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getTransactionFromDate() {
        return transactionFromDate;
    }

    public void setTransactionFromDate(Date transactionFromDate) {
        this.transactionFromDate = transactionFromDate;
    }

    public Date getTransactionToDate() {
        return transactionToDate;
    }

    public void setTransactionToDate(Date transactionToDate) {
        this.transactionToDate = transactionToDate;
    }

    public SimpleDateFormat getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    public void setStatusUpdateDateTime(SimpleDateFormat statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
    }

    public int getFinTechId() {
        return finTechId;
    }

    public void setFinTechId(int finTechId) {
        this.finTechId = finTechId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getReadAccountsBasic() {
        return readAccountsBasic;
    }

    public void setReadAccountsBasic(int readAccountsBasic) {
        this.readAccountsBasic = readAccountsBasic;
    }

    public int getReadAccountsDetail() {
        return readAccountsDetail;
    }

    public void setReadAccountsDetail(int readAccountsDetail) {
        this.readAccountsDetail = readAccountsDetail;
    }

    public int getReadBalances() {
        return readBalances;
    }

    public void setReadBalances(int readBalances) {
        this.readBalances = readBalances;
    }

    public int getReadStatementsDetail() {
        return readStatementsDetail;
    }

    public void setReadStatementsDetail(int readStatementsDetail) {
        this.readStatementsDetail = readStatementsDetail;
    }

    public int getReadStatementsBasic() {
        return readStatementsBasic;
    }

    public void setReadStatementsBasic(int readStatementsBasic) {
        this.readStatementsBasic = readStatementsBasic;
    }

    public int getReadTransactionsDetail() {
        return readTransactionsDetail;
    }

    public void setReadTransactionsDetail(int readTransactionsDetail) {
        this.readTransactionsDetail = readTransactionsDetail;
    }

    public int getReadTransactionsBasic() {
        return readTransactionsBasic;
    }

    public void setReadTransactionsBasic(int readTransactionsBasic) {
        this.readTransactionsBasic = readTransactionsBasic;
    }

    public int getReadTransactionsCredits() {
        return readTransactionsCredits;
    }

    public void setReadTransactionsCredits(int readTransactionsCredits) {
        this.readTransactionsCredits = readTransactionsCredits;
    }

    public int getReadTransactionsDebits() {
        return readTransactionsDebits;
    }

    public void setReadTransactionsDebits(int readTransactionsDebits) {
        this.readTransactionsDebits = readTransactionsDebits;
    }

    public AccountConsents() {
    }

    public AccountConsents(String accountConsentId, int clientId) {
        this.accountConsentId = accountConsentId;
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountConsents)) return false;
        AccountConsents that = (AccountConsents) o;
        return getId() == that.getId() && getFinTechId() == that.getFinTechId() && getClientId() == that.getClientId() && getReadAccountsBasic() == that.getReadAccountsBasic() && getReadAccountsDetail() == that.getReadAccountsDetail() && getReadBalances() == that.getReadBalances() && getReadStatementsDetail() == that.getReadStatementsDetail() && getReadStatementsBasic() == that.getReadStatementsBasic() && getReadTransactionsDetail() == that.getReadTransactionsDetail() && getReadTransactionsBasic() == that.getReadTransactionsBasic() && getReadTransactionsCredits() == that.getReadTransactionsCredits() && getReadTransactionsDebits() == that.getReadTransactionsDebits() && getAccountConsentId().equals(that.getAccountConsentId()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getCreationDateTime(), that.getCreationDateTime()) && Objects.equals(getExpirationDate(), that.getExpirationDate()) && Objects.equals(getTransactionFromDate(), that.getTransactionFromDate()) && Objects.equals(getTransactionToDate(), that.getTransactionToDate()) && Objects.equals(getStatusUpdateDateTime(), that.getStatusUpdateDateTime()) && Objects.equals(getApiKey(), that.getApiKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountConsentId(), getStatus(), getCreationDateTime(), getExpirationDate(), getTransactionFromDate(), getTransactionToDate(), getStatusUpdateDateTime(), getFinTechId(), getClientId(), getApiKey(), getReadAccountsBasic(), getReadAccountsDetail(), getReadBalances(), getReadStatementsDetail(), getReadStatementsBasic(), getReadTransactionsDetail(), getReadTransactionsBasic(), getReadTransactionsCredits(), getReadTransactionsDebits());
    }

    @Override
    public String toString() {
        return "AccountConsents{" +
                "id=" + id +
                ", accountConsentId='" + accountConsentId + '\'' +
                ", status='" + status + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", expirationDate=" + expirationDate +
                ", transactionFromDate=" + transactionFromDate +
                ", transactionToDate=" + transactionToDate +
                ", statusUpdateDateTime=" + statusUpdateDateTime +
                ", finTechId=" + finTechId +
                ", clientId=" + clientId +
                ", apiKey='" + apiKey + '\'' +
                ", readAccountsBasic=" + readAccountsBasic +
                ", readAccountsDetail=" + readAccountsDetail +
                ", readBalances=" + readBalances +
                ", readStatementsDetail=" + readStatementsDetail +
                ", readStatementsBasic=" + readStatementsBasic +
                ", readTransactionsDetail=" + readTransactionsDetail +
                ", readTransactionsBasic=" + readTransactionsBasic +
                ", readTransactionsCredits=" + readTransactionsCredits +
                ", readTransactionsDebits=" + readTransactionsDebits +
                '}';
    }
}