package by.openbanking.openbankingservice.data.entity;

import by.openbanking.openbankingservice.models.accinfo.OBCreditDebitCode1;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"statements"})
@ToString(callSuper = true, exclude = {"statements"})
@Entity
@Table(name = "OB_TRANSACTIONS")
public final class TransactionEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;

    @Column(name = "CREDIT_DEBIT_INDICATOR")
    @Enumerated(value = STRING)
    private OBCreditDebitCode1 creditDebitIndicator;

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
    private Set<StatementEntity> statements;

    public TransactionEntity() {
    }

}



