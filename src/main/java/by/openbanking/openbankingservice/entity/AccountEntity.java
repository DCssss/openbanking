package by.openbanking.openbankingservice.entity;

import by.openbanking.openbankingservice.models.accinfo.Account;
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

    @Column(name = "IDENTIFICATION")
    private String identification;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "OB_CONSENTS_2_ACCOUNTS",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSENT_ID")
    )
    private Set<ConsentEntity> consents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<StatementEntity> statements;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<TransactionListEntity> transactionLists;

    public AccountEntity() {
    }

}



