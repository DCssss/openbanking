package by.openbanking.openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "OB_FINTECHS")
public final class FintechEntity extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGIN")
    private String login;

    public FintechEntity() {
    }

}



