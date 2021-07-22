package by.openbanking.openbankingservice.entity;

import by.openbanking.openbankingservice.models.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"accounts"})
@ToString(callSuper = true, exclude = {"accounts"})
@Entity
@Table(name = "OB_CONSENTS")
public final class ConsentEntity extends BaseEntity<Long> {

    @Column(name = "STATUS")
    @Enumerated(value = STRING)
    private AccountConsentsStatus status;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINTECH_ID", nullable = false)
    private FintechEntity fintech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

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

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "OB_CONSENTS_2_ACCOUNTS",
            joinColumns = @JoinColumn(name = "CONSENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID")
    )
    private Set<AccountEntity> accounts;

    public static ConsentEntity valueOf(final ConsentData model) {
        ConsentEntity consent = new ConsentEntity();
        for (Permission permissionEnum : model.getPermissions()) {
            switch (permissionEnum) {
                case READACCOUNTSBASIC:
                    consent.readAccountsBasic = 1;
                    break;
                case READACCOUNTSDETAIL:
                    consent.readAccountsDetail = 1;
                    break;
                case READBALANCES:
                    consent.readBalances = 1;
                    break;
                case READSTATEMENTSDETAIL:
                    consent.readStatementsDetail = 1;
                    break;
                case READSTATEMENTSBASIC:
                    consent.readStatementsBasic = 1;
                    break;
                case READTRANSACTIONSBASIC:
                    consent.readTransactionsBasic = 1;
                    break;
                case READTRANSACTIONSDETAIL:
                    consent.readTransactionsDetail = 1;
                    break;
                case READTRANSACTIONSCREDITS:
                    consent.readTransactionsCredits = 1;
                    break;
                case READTRANSACTIONSDEBITS:
                    consent.readTransactionsDebits = 1;
                    break;
            }
        }
        consent.expirationDate = new Date(model.getExpirationDate().getTime());
        consent.transactionFromDate = new Date(model.getTransactionFromDate().getTime());
        consent.transactionToDate = new Date(model.getTransactionToDate().getTime());
        return consent;
    }


    public Collection<Permission> getPermission() {
        final List<Permission> permissions = new ArrayList<>();
        if (readAccountsBasic == 1) {
            permissions.add(Permission.READACCOUNTSBASIC);
        }
        if (readAccountsDetail == 1) {
            permissions.add(Permission.READACCOUNTSDETAIL);
        }
        if (readBalances == 1) {
            permissions.add(Permission.READBALANCES);
        }
        if (readStatementsBasic == 1) {
            permissions.add(Permission.READSTATEMENTSBASIC);
        }
        if (readStatementsDetail == 1) {
            permissions.add(Permission.READSTATEMENTSDETAIL);
        }
        if (readTransactionsBasic == 1) {
            permissions.add(Permission.READTRANSACTIONSBASIC);
        }
        if (readTransactionsDetail == 1) {
            permissions.add(Permission.READTRANSACTIONSDETAIL);
        }
        if (readTransactionsCredits == 1) {
            permissions.add(Permission.READTRANSACTIONSCREDITS);
        }
        if (readTransactionsDebits == 1) {
            permissions.add(Permission.READTRANSACTIONSDEBITS);
        }
        return permissions;
    }
}