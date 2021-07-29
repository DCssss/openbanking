package by.openbanking.openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"clients"})
@ToString(callSuper = true, exclude = {"clients"})
@Table(name = "OB_BANKS")
public final class BankEntity extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "IDENTIFIER")
    private String identifier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<ClientEntity> clients;

    public BankEntity() {
    }

}



