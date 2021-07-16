package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "OB_STATEMENTS")
public class Statements {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "STATEMENT_ID")
    private long statementID;

    @Column(name = "ACCOUNT_ID")
    private long accountID;

    @Column(name = "STATEMENT_FROM_BOOKING_DATE")
    private Date statementFromBookingDate;

    @Column(name = "STATEMENT_TO_BOOKING_DATE")
    private Date statementToBookingDate;

    @Column(name = "StatementCreateTime")
    private Date statementCreateTime;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "statements")
    private List<Transactions> transactionsList; */

    public Statements() {
    }

    public long getStatementID() {
        return statementID;
    }

    public void setStatementID(long statementID) {
        this.statementID = statementID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public Date getStatementFromBookingDate() {
        return statementFromBookingDate;
    }

    public void setStatementFromBookingDate(Date statementFromBookingDate) {
        this.statementFromBookingDate = statementFromBookingDate;
    }

    public Date getStatementToBookingDate() {
        return statementToBookingDate;
    }

    public void setStatementToBookingDate(Date statementToBookingDate) {
        this.statementToBookingDate = statementToBookingDate;
    }

    public Date getStatementCreateTime() {
        return statementCreateTime;
    }

    public void setStatementCreateTime(Date statementCreateTime) {
        this.statementCreateTime = statementCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statements)) return false;
        Statements that = (Statements) o;
        return getStatementID() == that.getStatementID() && getAccountID() == that.getAccountID() && Objects.equals(getStatementFromBookingDate(), that.getStatementFromBookingDate()) && Objects.equals(getStatementToBookingDate(), that.getStatementToBookingDate()) && Objects.equals(getStatementCreateTime(), that.getStatementCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatementID(), getAccountID(), getStatementFromBookingDate(), getStatementToBookingDate(), getStatementCreateTime());
    }

    @Override
    public String toString() {
        return "Statements{" +
                "statementID=" + statementID +
                ", accountID=" + accountID +
                ", statementFromBookingDate=" + statementFromBookingDate +
                ", statementToBookingDate=" + statementToBookingDate +
                ", statementCreateTime=" + statementCreateTime +
                '}';
    }
}



