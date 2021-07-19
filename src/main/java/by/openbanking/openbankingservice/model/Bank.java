package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "OB_BANKS")
public final class Bank extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "IDENTIFIER")
    private String identifier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<Client> clients;

    public Bank() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(identifier, bank.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identifier);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}



