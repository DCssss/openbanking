package by.openbanking.openbankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "OB_FINTECHS")
public final class Fintech extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGIN")
    private String login;

    public Fintech() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fintech fintech = (Fintech) o;
        return Objects.equals(id, fintech.id) && Objects.equals(name, fintech.name) && Objects.equals(login, fintech.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login);
    }

    @Override
    public String toString() {
        return "Fintech{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}



