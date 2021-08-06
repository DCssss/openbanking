package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Базовая сущность.
 *
 * @param <T> Тип данных идентификатора сущности.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@MappedSuperclass
abstract class BaseEntity<T extends Serializable> {

    /**
     * Идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private T id;
}
