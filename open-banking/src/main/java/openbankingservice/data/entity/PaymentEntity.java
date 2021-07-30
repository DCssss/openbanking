package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Платеж.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "OB_PAYMENTS")
public final class PaymentEntity extends BaseEntity<Long> {

    /**
     * Платежное указание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_CONSENT_ID")
    private PaymentConsentEntity paymentConsent;

    /**
     * Тип платежа
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * Дата и время создания платежа
     */
    @Column(name = "CREATION_TIME")
    private Date createTime;

    /**
     * Статус платежа
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * Дата и время обновления статуса
     */
    @Column(name = "STATUS_UPDATE_TIME")
    private Date statusUpdateTime;

    public PaymentEntity() {
    }

}
