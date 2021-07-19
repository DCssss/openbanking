package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(client, account.client) && Objects.equals(status, account.status) && Objects.equals(statusUpdateTime, account.statusUpdateTime) && Objects.equals(balanceAmount, account.balanceAmount) && Objects.equals(currency, account.currency) && Objects.equals(creationTime, account.creationTime) && Objects.equals(description, account.description) && Objects.equals(name, account.name) && Objects.equals(type, account.type) && Objects.equals(subType, account.subType) && Objects.equals(identification, account.identification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, status, statusUpdateTime, balanceAmount, currency, creationTime, description, name, type, subType, identification);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", status='" + status + '\'' +
                ", statusUpdateTime=" + statusUpdateTime +
                ", balanceAmount=" + balanceAmount +
                ", currency='" + currency + '\'' +
                ", creationTime=" + creationTime +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                ", identification='" + identification + '\'' +
                '}';
    }
}



