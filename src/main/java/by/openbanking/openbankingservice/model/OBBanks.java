package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OBBanks")
public class OBBanks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BankId")
    private long bankId;

    @Column(name = "BankName")
    private String bankName;

    @Column(name = "BankIdentifier")
    private String bankIdentifier;

    public OBBanks() {
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIdentifier() {
        return bankIdentifier;
    }

    public void setBankIdentifier(String bankIdentifier) {
        this.bankIdentifier = bankIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OBBanks)) return false;
        OBBanks obBanks = (OBBanks) o;
        return getBankId() == obBanks.getBankId() && getBankName().equals(obBanks.getBankName()) && getBankIdentifier().equals(obBanks.getBankIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankId(), getBankName(), getBankIdentifier());
    }

    @Override
    public String toString() {
        return "OBBanks{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", bankIdentifier='" + bankIdentifier + '\'' +
                '}';
    }
}



