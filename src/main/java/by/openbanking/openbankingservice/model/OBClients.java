package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OBClients")
public class OBClients {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ClientId")
    private long clientId;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "BankId")
    private long bankId;

    @Column(name = "ClientAddress")
    private String clientAddress;

    @Column(name = "ClientTax")
    private String clientTax;

    public OBClients() {
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientTax() {
        return clientTax;
    }

    public void setClientTax(String clientTax) {
        this.clientTax = clientTax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OBClients)) return false;
        OBClients obClients = (OBClients) o;
        return getClientId() == obClients.getClientId() && getBankId() == obClients.getBankId() && getClientName().equals(obClients.getClientName()) && Objects.equals(getClientAddress(), obClients.getClientAddress()) && Objects.equals(getClientTax(), obClients.getClientTax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId(), getClientName(), getBankId(), getClientAddress(), getClientTax());
    }

    @Override
    public String toString() {
        return "OBClients{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", bankId=" + bankId +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientTax='" + clientTax + '\'' +
                '}';
    }
}



