package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "OB_TRANSACTIONS")
public final class Transaction extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "CREDIT_DEBIT_INDICATOR")
    private long creditDebitIndicator;

    @Column(name = "BOOKING_TIME")
    private Date bookingTime;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "DEBIT_TAX_IDENTIFICATION")
    private String debitTaxIdentification;

    @Column(name = "DEBIT_NAME")
    private String debitName;

    @Column(name = "DEBIT_ACC_IDENTIFICATION")
    private String debitAccIdentification;

    @Column(name = "DEBIT_BANK_NAME")
    private String debitBankName;

    @Column(name = "DEBIT_BANK_IDENTIFICATION")
    private String debitBankIdentification;

    @Column(name = "CREDIT_TAX_IDENTIFICATION")
    private String creditTaxIdentification;

    @Column(name = "CREDIT_NAME")
    private String creditName;

    @Column(name = "CREDIT_ACC_IDENTIFICATION")
    private String creditAccIdentification;

    @Column(name = "CREDIT_BANK_NAME")
    private String creditBankName;

    @Column(name = "CREDIT_BANK_IDENTIFICATION")
    private String creditBankIdentification;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "OB_STATEMENTS_2_TRANSACTIONS",
            joinColumns = @JoinColumn(name = "TRANSACTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "STATEMENT_ID")
    )
    private Set<Statement> statements;

    public Transaction() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && creditDebitIndicator == that.creditDebitIndicator && Objects.equals(account, that.account) && Objects.equals(bookingTime, that.bookingTime) && Objects.equals(number, that.number) && Objects.equals(amount, that.amount) && Objects.equals(currency, that.currency) && Objects.equals(details, that.details) && Objects.equals(debitTaxIdentification, that.debitTaxIdentification) && Objects.equals(debitName, that.debitName) && Objects.equals(debitAccIdentification, that.debitAccIdentification) && Objects.equals(debitBankName, that.debitBankName) && Objects.equals(debitBankIdentification, that.debitBankIdentification) && Objects.equals(creditTaxIdentification, that.creditTaxIdentification) && Objects.equals(creditName, that.creditName) && Objects.equals(creditAccIdentification, that.creditAccIdentification) && Objects.equals(creditBankName, that.creditBankName) && Objects.equals(creditBankIdentification, that.creditBankIdentification) && Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, creditDebitIndicator, bookingTime, number, amount, currency, details, debitTaxIdentification, debitName, debitAccIdentification, debitBankName, debitBankIdentification, creditTaxIdentification, creditName, creditAccIdentification, creditBankName, creditBankIdentification, statements);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", creditDebitIndicator=" + creditDebitIndicator +
                ", bookingTime=" + bookingTime +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", details='" + details + '\'' +
                ", debitTaxIdentification='" + debitTaxIdentification + '\'' +
                ", debitName='" + debitName + '\'' +
                ", debitAccIdentification='" + debitAccIdentification + '\'' +
                ", debitBankName='" + debitBankName + '\'' +
                ", debitBankIdentification='" + debitBankIdentification + '\'' +
                ", creditTaxIdentification='" + creditTaxIdentification + '\'' +
                ", creditName='" + creditName + '\'' +
                ", creditAccIdentification='" + creditAccIdentification + '\'' +
                ", creditBankName='" + creditBankName + '\'' +
                ", creditBankIdentification='" + creditBankIdentification + '\'' +
                ", statements=" + statements +
                '}';
    }
}



