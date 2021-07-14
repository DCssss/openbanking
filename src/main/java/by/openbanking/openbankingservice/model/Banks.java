package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OB_BANKS")
public class Banks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "BANK_ID")
    private long bankId;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_IDENTIFIER")
    private String bankIdentifier;

    /*@OneToOne(mappedBy = "OB_BANKS")
    private Clients clients;*/

    public Banks() {
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
        if (!(o instanceof Banks)) return false;
        Banks banks = (Banks) o;
        return getBankId() == banks.getBankId() && getBankName().equals(banks.getBankName()) && getBankIdentifier().equals(banks.getBankIdentifier());
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



