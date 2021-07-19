package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "OB_CLIENTS")
public final class Client extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ID", nullable = false)
    private Bank bank;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TAX")
    private String tax;

    public Client() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(bank, client.bank) && Objects.equals(address, client.address) && Objects.equals(tax, client.tax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bank, address, tax);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bank=" + bank +
                ", address='" + address + '\'' +
                ", tax='" + tax + '\'' +
                '}';
    }
}



