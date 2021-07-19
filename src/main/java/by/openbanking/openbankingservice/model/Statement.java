package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "OB_STATEMENTS")
public final class Statement extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Column(name = "FROM_BOOKING_DATE")
    private Date fromBookingDate;

    @Column(name = "TO_BOOKING_DATE")
    private Date toBookingDate;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "OB_STATEMENTS_2_TRANSACTIONS",
            joinColumns = @JoinColumn(name = "STATEMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "TRANSACTION_ID")
    )
    private Set<Transaction> transactions;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "statements")
    private List<Transactions> transactionsList; */

    public Statement() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return Objects.equals(id, statement.id) && Objects.equals(account, statement.account) && Objects.equals(fromBookingDate, statement.fromBookingDate) && Objects.equals(toBookingDate, statement.toBookingDate) && Objects.equals(createTime, statement.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, fromBookingDate, toBookingDate, createTime);
    }

    @Override
    public String toString() {
        return "Statement{" +
                "id=" + id +
                ", account=" + account +
                ", fromBookingDate=" + fromBookingDate +
                ", toBookingDate=" + toBookingDate +
                ", createTime=" + createTime +
                '}';
    }
}



