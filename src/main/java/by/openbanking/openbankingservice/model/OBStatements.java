package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OBStatements")
public class OBStatements {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "StatementID")
    private long statementID;

    @Column(name = "AccountID")
    private long accountID;

    @Column(name = "StatementFromBokingDate")
    private Date statementFromBokingDate;

    @Column(name = "StatementToBokingDate")
    private Date statementToBokingDate;

    @Column(name = "StatementCreateTime")
    private Date statementCreateTime;

    public OBStatements() {
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

    public Date getStatementFromBokingDate() {
        return statementFromBokingDate;
    }

    public void setStatementFromBokingDate(Date statementFromBokingDate) {
        this.statementFromBokingDate = statementFromBokingDate;
    }

    public Date getStatementToBokingDate() {
        return statementToBokingDate;
    }

    public void setStatementToBokingDate(Date statementToBokingDate) {
        this.statementToBokingDate = statementToBokingDate;
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
        if (!(o instanceof OBStatements)) return false;
        OBStatements that = (OBStatements) o;
        return getStatementID() == that.getStatementID() && getAccountID() == that.getAccountID() && Objects.equals(getStatementFromBokingDate(), that.getStatementFromBokingDate()) && Objects.equals(getStatementToBokingDate(), that.getStatementToBokingDate()) && Objects.equals(getStatementCreateTime(), that.getStatementCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatementID(), getAccountID(), getStatementFromBokingDate(), getStatementToBokingDate(), getStatementCreateTime());
    }

    @Override
    public String toString() {
        return "OBStatements{" +
                "statementID=" + statementID +
                ", accountID=" + accountID +
                ", statementFromBokingDate=" + statementFromBokingDate +
                ", statementToBokingDate=" + statementToBokingDate +
                ", statementCreateTime=" + statementCreateTime +
                '}';
    }
}



