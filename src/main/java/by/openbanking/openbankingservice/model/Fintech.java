package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OB_FINTECH")
public class Fintech {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "FINTECH_ID")
    private long fintechId;

    @Column(name = "FINTECH_NAME")
    private String fintechName;

    @Column(name = "FINTECH_LOGIN")
    private String fintechLogin;

    public Fintech() {
    }

    public long getFintechId() {
        return fintechId;
    }

    public void setFintechId(long fintechId) {
        this.fintechId = fintechId;
    }

    public String getFintechName() {
        return fintechName;
    }

    public void setFintechName(String fintechName) {
        this.fintechName = fintechName;
    }

    public String getFintechLogin() {
        return fintechLogin;
    }

    public void setFintechLogin(String fintechLogin) {
        this.fintechLogin = fintechLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fintech)) return false;
        Fintech fintech = (Fintech) o;
        return getFintechId() == fintech.getFintechId() && getFintechName().equals(fintech.getFintechName()) && Objects.equals(getFintechLogin(), fintech.getFintechLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFintechId(), getFintechName(), getFintechLogin());
    }

    @Override
    public String toString() {
        return "OBFintech{" +
                "fintechId=" + fintechId +
                ", fintechName='" + fintechName + '\'' +
                ", fintechLogin='" + fintechLogin + '\'' +
                '}';
    }
}



