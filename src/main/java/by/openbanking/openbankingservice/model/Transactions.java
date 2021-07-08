package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OB_TRANSACTIONS")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TRANSACTION_ID")
    private long transactionID;

    @Column(name = "ACCOUNT_ID")
    private long accountID;

    @Column(name = "TRANSACTION_CREDIT_DEBIT_INDICATOR")
    private long transactionСreditDebitIndicator;

    @Column(name = "TRANSACTION_BOOKING_TIME")
    private Date transactionBookingTime;

    @Column(name = "TRANSACTION_NUMBER")
    private String transactionNumber;

    @Column(name = "TRANSACTION_AMOUNT")
    private double transactionAmount;

    @Column(name = "TRANSACTION_CURRENCY")
    private String transactionCurrency;

    @Column(name = "TRANSACTION_DETAILS")
    private String transactionDetails;

    @Column(name = "TRANSACTION_DEBIT_TAX_IDENTIFICATION")
    private String transactionDebitTaxIdentification;

    @Column(name = "TRANSACTION_DEBIT_NAME")
    private String transactionDebitName;

    @Column(name = "TRANSACTION_DEBIT_ACC_IDENTIFICATION")
    private String transactionDebitAccIdentification;

    @Column(name = "TRANSACTION_DEBIT_BANK_NAME")
    private String transactionDebitBankName;

    @Column(name = "TRANSACTION_DEBIT_BANK_IDENTIFICATION")
    private String transactionDebitBankIdentification;

    @Column(name = "TRANSACTION_CREDIT_TAX_IDENTIFICATION")
    private String transactionCreditTaxIdentification;

    @Column(name = "TRANSACTION_CREDIT_NAME")
    private String transactionCreditName;

    @Column(name = "TRANSACTION_CREDIT_ACC_IDENTIFICATION")
    private String transactionCreditAccIdentification;

    @Column(name = "TRANSACTION_CREDIT_BANK_NAME")
    private String transactionCreditBankName;

    @Column(name = "TRANSACTION_CREDIT_BANK_IDENTIFICATION")
    private String transactionCreditBankIdentification;

    public Transactions() {
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public long getTransactionСreditDebitIndicator() {
        return transactionСreditDebitIndicator;
    }

    public void setTransactionСreditDebitIndicator(long transactionСreditDebitIndicator) {
        this.transactionСreditDebitIndicator = transactionСreditDebitIndicator;
    }

    public Date getTransactionBookingTime() {
        return transactionBookingTime;
    }

    public void setTransactionBookingTime(Date transactionBookingTime) {
        this.transactionBookingTime = transactionBookingTime;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionDebitTaxIdentification() {
        return transactionDebitTaxIdentification;
    }

    public void setTransactionDebitTaxIdentification(String transactionDebitTaxIdentification) {
        this.transactionDebitTaxIdentification = transactionDebitTaxIdentification;
    }

    public String getTransactionDebitName() {
        return transactionDebitName;
    }

    public void setTransactionDebitName(String transactionDebitName) {
        this.transactionDebitName = transactionDebitName;
    }

    public String getTransactionDebitAccIdentification() {
        return transactionDebitAccIdentification;
    }

    public void setTransactionDebitAccIdentification(String transactionDebitAccIdentification) {
        this.transactionDebitAccIdentification = transactionDebitAccIdentification;
    }

    public String getTransactionDebitBankName() {
        return transactionDebitBankName;
    }

    public void setTransactionDebitBankName(String transactionDebitBankName) {
        this.transactionDebitBankName = transactionDebitBankName;
    }

    public String getTransactionDebitBankIdentification() {
        return transactionDebitBankIdentification;
    }

    public void setTransactionDebitBankIdentification(String transactionDebitBankIdentification) {
        this.transactionDebitBankIdentification = transactionDebitBankIdentification;
    }

    public String getTransactionCreditTaxIdentification() {
        return transactionCreditTaxIdentification;
    }

    public void setTransactionCreditTaxIdentification(String transactionCreditTaxIdentification) {
        this.transactionCreditTaxIdentification = transactionCreditTaxIdentification;
    }

    public String getTransactionCreditName() {
        return transactionCreditName;
    }

    public void setTransactionCreditName(String transactionCreditName) {
        this.transactionCreditName = transactionCreditName;
    }

    public String getTransactionCreditAccIdentification() {
        return transactionCreditAccIdentification;
    }

    public void setTransactionCreditAccIdentification(String transactionCreditAccIdentification) {
        this.transactionCreditAccIdentification = transactionCreditAccIdentification;
    }

    public String getTransactionCreditBankName() {
        return transactionCreditBankName;
    }

    public void setTransactionCreditBankName(String transactionCreditBankName) {
        this.transactionCreditBankName = transactionCreditBankName;
    }

    public String getTransactionCreditBankIdentification() {
        return transactionCreditBankIdentification;
    }

    public void setTransactionCreditBankIdentification(String transactionCreditBankIdentification) {
        this.transactionCreditBankIdentification = transactionCreditBankIdentification;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transactions)) return false;
        Transactions that = (Transactions) o;
        return getTransactionID() == that.getTransactionID() && getAccountID() == that.getAccountID() && getTransactionСreditDebitIndicator() == that.getTransactionСreditDebitIndicator() && Double.compare(that.getTransactionAmount(), getTransactionAmount()) == 0 && Objects.equals(getTransactionBookingTime(), that.getTransactionBookingTime()) && Objects.equals(getTransactionNumber(), that.getTransactionNumber()) && Objects.equals(getTransactionCurrency(), that.getTransactionCurrency()) && Objects.equals(getTransactionDetails(), that.getTransactionDetails()) && Objects.equals(getTransactionNumber(), that.getTransactionNumber()) && Objects.equals(getTransactionDebitTaxIdentification(), that.getTransactionDebitTaxIdentification()) && Objects.equals(getTransactionDebitName(), that.getTransactionDebitName()) && Objects.equals(getTransactionDebitAccIdentification(), that.getTransactionDebitAccIdentification()) && Objects.equals(getTransactionDebitBankName(), that.getTransactionDebitBankName()) && Objects.equals(getTransactionDebitBankIdentification(), that.getTransactionDebitBankIdentification()) && Objects.equals(getTransactionCreditTaxIdentification(), that.getTransactionCreditTaxIdentification()) && Objects.equals(getTransactionCreditName(), that.getTransactionCreditName()) && Objects.equals(getTransactionCreditAccIdentification(), that.getTransactionCreditAccIdentification()) && Objects.equals(getTransactionCreditBankName(), that.getTransactionCreditBankName()) && Objects.equals(getTransactionCreditBankIdentification(), that.getTransactionCreditBankIdentification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionID(), getAccountID(), getTransactionСreditDebitIndicator(), getTransactionBookingTime(), getTransactionNumber(), getTransactionAmount(), getTransactionCurrency(), getTransactionDetails(), getTransactionNumber(), getTransactionDebitTaxIdentification(), getTransactionDebitName(), getTransactionDebitAccIdentification(), getTransactionDebitBankName(), getTransactionDebitBankIdentification(), getTransactionCreditTaxIdentification(), getTransactionCreditName(), getTransactionCreditAccIdentification(), getTransactionCreditBankName(), getTransactionCreditBankIdentification());
    }

    @Override
    public String toString() {
        return "OBTransactions{" +
                "transactionID=" + transactionID +
                ", accountID=" + accountID +
                ", transactionСreditDebitIndicator=" + transactionСreditDebitIndicator +
                ", transactionBookingTime=" + transactionBookingTime +
                ", transactionNumber='" + transactionNumber + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionCurrency='" + transactionCurrency + '\'' +
                ", transactionDetails='" + transactionDetails + '\'' +
                ", transactionNumber='" + transactionNumber + '\'' +
                ", transactionDebitTaxIdentification='" + transactionDebitTaxIdentification + '\'' +
                ", transactionDebitName='" + transactionDebitName + '\'' +
                ", transactionDebitAccIdentification='" + transactionDebitAccIdentification + '\'' +
                ", transactionDebitBankName='" + transactionDebitBankName + '\'' +
                ", transactionDebitBankIdentification='" + transactionDebitBankIdentification + '\'' +
                ", transactionCreditTaxIdentification='" + transactionCreditTaxIdentification + '\'' +
                ", transactionCreditName='" + transactionCreditName + '\'' +
                ", transactionCreditAccIdentification='" + transactionCreditAccIdentification + '\'' +
                ", transactionCreditBankName='" + transactionCreditBankName + '\'' +
                ", transactionCreditBankIdentification='" + transactionCreditBankIdentification + '\'' +
                '}';
    }
}



