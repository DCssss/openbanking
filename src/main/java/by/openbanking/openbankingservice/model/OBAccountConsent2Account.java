package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OBAccountConsent2Account")
public class OBAccountConsent2Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id")
    private long id;

    @Column(name = "AccountConsentID")
    private long accountConsentID;

    @Column(name = "AccountID")
    private long accountID;

    public OBAccountConsent2Account() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountConsentID() {
        return accountConsentID;
    }

    public void setAccountConsentID(long accountConsentID) {
        this.accountConsentID = accountConsentID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OBAccountConsent2Account)) return false;
        OBAccountConsent2Account that = (OBAccountConsent2Account) o;
        return getId() == that.getId() && getAccountConsentID() == that.getAccountConsentID() && getAccountID() == that.getAccountID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountConsentID(), getAccountID());
    }

    @Override
    public String toString() {
        return "OBAccountConsent2Account{" +
                "id=" + id +
                ", accountConsentID=" + accountConsentID +
                ", accountID=" + accountID +
                '}';
    }
}




