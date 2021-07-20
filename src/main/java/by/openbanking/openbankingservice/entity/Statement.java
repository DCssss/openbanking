package by.openbanking.openbankingservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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

    public Statement() {
    }

}



