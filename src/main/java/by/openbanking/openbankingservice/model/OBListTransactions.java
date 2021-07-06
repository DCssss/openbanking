package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OBListTransactions")
public class OBListTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ListTransactionID")
    private long listTransactionID;

    @Column(name = "AccountID")
    private long accountID;

    @Column(name = "ListTransactionFromBokingTime")
    private Date listTransactionFromBokingTime;

    @Column(name = "ListTransactionToBokingTime")
    private Date listTransactionToBokingTime;

    @Column(name = "ListTransactionCreateTime")
    private Date listTransactionCreateTime;

    public OBListTransactions() {
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

    public Date getListTransactionFromBokingTime() {
        return listTransactionFromBokingTime;
    }

    public void setListTransactionFromBokingTime(Date listTransactionFromBokingTime) {
        this.listTransactionFromBokingTime = listTransactionFromBokingTime;
    }

    public Date getListTransactionToBokingTime() {
        return listTransactionToBokingTime;
    }

    public void setListTransactionToBokingTime(Date listTransactionToBokingTime) {
        this.listTransactionToBokingTime = listTransactionToBokingTime;
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
        if (!(o instanceof OBListTransactions)) return false;
        OBListTransactions that = (OBListTransactions) o;
        return getListTransactionID() == that.getListTransactionID() && getAccountID() == that.getAccountID() && Objects.equals(getListTransactionFromBokingTime(), that.getListTransactionFromBokingTime()) && Objects.equals(getListTransactionToBokingTime(), that.getListTransactionToBokingTime()) && Objects.equals(getListTransactionCreateTime(), that.getListTransactionCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListTransactionID(), getAccountID(), getListTransactionFromBokingTime(), getListTransactionToBokingTime(), getListTransactionCreateTime());
    }

    @Override
    public String toString() {
        return "OBListTransactions{" +
                "listTransactionID=" + listTransactionID +
                ", accountID=" + accountID +
                ", listTransactionFromBokingTime=" + listTransactionFromBokingTime +
                ", listTransactionToBokingTime=" + listTransactionToBokingTime +
                ", listTransactionCreateTime=" + listTransactionCreateTime +
                '}';
    }
}



