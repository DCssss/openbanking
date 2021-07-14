package by.openbanking.openbankingservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OB_CLIENTS")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CLIENT_ID")
    private long clientId;

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Column(name = "BANK_ID")
    private long bankId;

    @Column(name = "CLIENT_ADDRESS")
    private String clientAddress;

    @Column(name = "CLIENT_TAX")
    private String clientTax;

   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OB_BANKS_BANK_ID", referencedColumnName = "BANK_ID")
    private Banks banks; */

    public Clients() {
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
        if (!(o instanceof Clients)) return false;
        Clients clients = (Clients) o;
        return getClientId() == clients.getClientId() && getBankId() == clients.getBankId() && getClientName().equals(clients.getClientName()) && Objects.equals(getClientAddress(), clients.getClientAddress()) && Objects.equals(getClientTax(), clients.getClientTax());
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



