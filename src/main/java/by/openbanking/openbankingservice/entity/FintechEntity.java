package by.openbanking.openbankingservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

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



