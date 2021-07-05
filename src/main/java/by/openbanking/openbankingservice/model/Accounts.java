package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "accountId")
    private long accountId;

    @Column(name = "status")
    private String status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "accountSubType")
    private String CurrentAccount;

    @Column(name = "creationDateTime")
    private String creationDateTime;

    public Accounts() {

    }

    public Accounts(long id, long accountId, String status, String currency, String accountType, String currentAccount, String creationDateTime) {
        this.id = id;
        this.accountId = accountId;
        this.status = status;
        this.currency = currency;
        this.accountType = accountType;
        CurrentAccount = currentAccount;
        this.creationDateTime = creationDateTime;
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCurrentAccount() {
        return CurrentAccount;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setCurrentAccount(String currentAccount) {
        CurrentAccount = currentAccount;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accounts)) return false;
        Accounts accounts = (Accounts) o;
        return getId() == accounts.getId() && getAccountId() == accounts.getAccountId() && getStatus().equals(accounts.getStatus()) && getCurrency().equals(accounts.getCurrency()) && Objects.equals(getAccountType(), accounts.getAccountType()) && Objects.equals(getCurrentAccount(), accounts.getCurrentAccount()) && Objects.equals(getCreationDateTime(), accounts.getCreationDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountId(), getStatus(), getCurrency(), getAccountType(), getCurrentAccount(), getCreationDateTime());
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", status='" + status + '\'' +
                ", currency='" + currency + '\'' +
                ", accountType='" + accountType + '\'' +
                ", CurrentAccount='" + CurrentAccount + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                '}';
    }
}



