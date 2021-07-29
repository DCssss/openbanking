package by.openbanking.openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "OB_TRANSACTION_LISTS")
public final class TransactionListEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;

    @Column(name = "FROM_BOOKING_TIME")
    private Date fromBookingTime;

    @Column(name = "TO_BOOKING_TIME")
    private Date toBookingTime;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    public TransactionListEntity() {
    }

}



