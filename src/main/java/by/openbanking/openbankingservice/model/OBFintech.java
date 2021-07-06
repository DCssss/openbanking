package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OBFintech")
public class OBFintech {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FintechId")
    private long fintechId;

    @Column(name = "FintechName")
    private String fintechName;

    @Column(name = "FintechLogin")
    private String fintechLogin;

    public OBFintech() {
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
        if (!(o instanceof OBFintech)) return false;
        OBFintech obFintech = (OBFintech) o;
        return getFintechId() == obFintech.getFintechId() && getFintechName().equals(obFintech.getFintechName()) && Objects.equals(getFintechLogin(), obFintech.getFintechLogin());
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



