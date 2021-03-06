package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openbankingservice.models.accinfo.Account;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Счет.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"consents", "statements", "transactionLists"})
@ToString(callSuper = true, exclude = {"consents", "statements", "transactionLists"})
@Entity
@Table(name = "OB_ACCOUNTS")
public final class AccountEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private ClientEntity client;

    @Column(name = "STATUS")
    @Enumerated(value = STRING)
    private Account.StatusEnum status;

    @Column(name = "STATUS_UPDATE_TIME")
    private Date statusUpdateTime;

    @Column(name = "BALANCE_AMOUNT")
    private BigDecimal balanceAmount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SUB_TYPE")
    @Enumerated(value = STRING)
    private Account.AccountSubTypeEnum subType;

    @Column(name = "IDENTIFICATION", unique = true)
    private String identification;

    @ManyToMany(fetch = LAZY, mappedBy = "accounts")
    private Set<ConsentEntity> consents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<StatementEntity> statements;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<TransactionListEntity> transactionLists;

    @OneToMany(fetch = LAZY, mappedBy = "account")
    private List<TransactionEntity> transactionList;

    public AccountEntity() {
    }

}



