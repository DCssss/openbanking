package by.openbanking.openbankingservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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

}



