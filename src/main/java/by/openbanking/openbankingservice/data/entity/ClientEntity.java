package by.openbanking.openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"accounts", "consents"})
@ToString(callSuper = true, exclude = {"accounts", "consents"})
@Entity
@Table(name = "OB_CLIENTS")
public final class ClientEntity extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ID", nullable = false)
    private BankEntity bank;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TAX")
    private String tax;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<AccountEntity> accounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ConsentEntity> consents;

    public ClientEntity() {
    }

}



