package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/**
 * Выписка по счету.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"transactions"})
@ToString(callSuper = true, exclude = {"transactions"})
@Entity
@Table(name = "OB_STATEMENTS")
public final class StatementEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private AccountEntity account;

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
    private Set<TransactionEntity> transactions;

    public StatementEntity() {
    }

}



