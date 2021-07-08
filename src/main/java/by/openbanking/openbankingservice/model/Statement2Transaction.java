package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OB_STATEMENT_2_TRANSACTION")
public class Statement2Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @Column(name = "STATEMENT_ID")
    private long statementID;

    @Column(name = "TRANSACTION_ID")
    private long transactionID;

    public Statement2Transaction() {
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
        if (!(o instanceof Statement2Transaction)) return false;
        Statement2Transaction that = (Statement2Transaction) o;
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




