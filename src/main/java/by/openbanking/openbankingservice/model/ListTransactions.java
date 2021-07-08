package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OB_LIST_TRANSACTIONS")
public class ListTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIST_TRANSACTION_ID")
    private long listTransactionID;

    @Column(name = "ACCOUNT_ID")
    private long accountID;

    @Column(name = "LIST_TRANSACTION_FROM_BOOKING_TIME")
    private Date listTransactionFromBookingTime;

    @Column(name = "LIST_TRANSACTION_TO_BOOKING_TIME")
    private Date listTransactionToBookingTime;

    @Column(name = "LIST_TRANSACTION_CREATE_TIME")
    private Date listTransactionCreateTime;

    public ListTransactions() {
    }

    public long getListTransactionID() {
        return listTransactionID;
    }

    public void setListTransactionID(long listTransactionID) {
        this.listTransactionID = listTransactionID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public Date getListTransactionFromBookingTime() {
        return listTransactionFromBookingTime;
    }

    public void setListTransactionFromBookingTime(Date listTransactionFromBookingTime) {
        this.listTransactionFromBookingTime = listTransactionFromBookingTime;
    }

    public Date getListTransactionToBookingTime() {
        return listTransactionToBookingTime;
    }

    public void setListTransactionToBookingTime(Date listTransactionToBookingTime) {
        this.listTransactionToBookingTime = listTransactionToBookingTime;
    }

    public Date getListTransactionCreateTime() {
        return listTransactionCreateTime;
    }

    public void setListTransactionCreateTime(Date listTransactionCreateTime) {
        this.listTransactionCreateTime = listTransactionCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTransactions)) return false;
        ListTransactions that = (ListTransactions) o;
        return getListTransactionID() == that.getListTransactionID() && getAccountID() == that.getAccountID() && Objects.equals(getListTransactionFromBookingTime(), that.getListTransactionFromBookingTime()) && Objects.equals(getListTransactionToBookingTime(), that.getListTransactionToBookingTime()) && Objects.equals(getListTransactionCreateTime(), that.getListTransactionCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListTransactionID(), getAccountID(), getListTransactionFromBookingTime(), getListTransactionToBookingTime(), getListTransactionCreateTime());
    }

    @Override
    public String toString() {
        return "ListTransactions{" +
                "listTransactionID=" + listTransactionID +
                ", accountID=" + accountID +
                ", listTransactionFromBookingTime=" + listTransactionFromBookingTime +
                ", listTransactionToBookingTime=" + listTransactionToBookingTime +
                ", listTransactionCreateTime=" + listTransactionCreateTime +
                '}';
    }
}



