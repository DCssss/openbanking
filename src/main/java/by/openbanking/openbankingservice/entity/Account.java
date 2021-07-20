package by.openbanking.openbankingservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "OB_ACCOUNTS")
public final class Account extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @Column(name = "STATUS")
    @Enumerated(value = STRING)
    private by.openbanking.openbankingservice.models.Account.StatusEnum status;

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
    private by.openbanking.openbankingservice.models.Account.AccountSubTypeEnum subType;

    @Column(name = "IDENTIFICATION")
    private String identification;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "OB_CONSENTS_2_ACCOUNTS",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSENT_ID")
    )
    private Set<Consent> consents;

    public Account() {
    }

}



