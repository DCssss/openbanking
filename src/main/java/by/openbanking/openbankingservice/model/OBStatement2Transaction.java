package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OBStatement2Transaction")
public class OBStatement2Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long id;

    @Column(name = "StatementID")
    private long statementID;

    @Column(name = "TransactionID")
    private long transactionID;

    public OBStatement2Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStatementID() {
        return statementID;
    }

    public void setStatementID(long statementID) {
        this.statementID = statementID;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OBStatement2Transaction)) return false;
        OBStatement2Transaction that = (OBStatement2Transaction) o;
        return getId() == that.getId() && getStatementID() == that.getStatementID() && getTransactionID() == that.getTransactionID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatementID(), getTransactionID());
    }

    @Override
    public String toString() {
        return "OBStatement2Transaction{" +
                "id=" + id +
                ", statementID=" + statementID +
                ", transactionID=" + transactionID +
                '}';
    }
}




