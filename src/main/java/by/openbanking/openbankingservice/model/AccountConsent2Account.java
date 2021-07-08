package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OB_ACCOUNTCONSENT_2_ACCOUNT")
public class AccountConsent2Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @Column(name = "ACCOUNT_CONSENT_ID")
    private long accountConsentID;

    @Column(name = "ACCOUNT_ID")
    private long accountID;

    public AccountConsent2Account() {
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
        if (!(o instanceof AccountConsent2Account)) return false;
        AccountConsent2Account that = (AccountConsent2Account) o;
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




