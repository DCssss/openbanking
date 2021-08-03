package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

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
     * Статусы платежа (Справочник ISO Е065)
     */
    public enum Status {
        /**
         * Валидация и проверка профиля прошли успешно
         */
        ACCP,
        /**
         * Денежные средства зачислены на счет бенефициара
         */
        ACCC,
        /**
         * Денежные средства списаны со счета плательщика
         */
        ACSC,
        /**
         * Все проверки пройдены успешно, инициирование перевода принято к исполнению
         */
        ACSP,
        /**
         * Аутентификация, синтаксическая и семантическая проверки прошли успешно
         */
        ACTC,
        /**
         * Инструкция принята, но будут внесены изменения, например дата или дополнительная информация не получены
         */
        ACWC,
        /**
         * Некоторые транзакции были приняты, а остальные транзакции еще не имеют статуса "принято"
         */
        PART,
        /**
         * Инициирование перевода или отдельная транзакция ожидают рассмотрения. Будут выполнены дальнейшие проверки и
         * обновление статуса
         */
        PDNG,
        /**
         * Платежный документ получен банком-получателем
         */
        RCVD,
        /**
         * Платежный документ или отдельная транзакция отклонены
         */
        RJCT
    }

    /**
     * Типы платежней
     */
    public enum Type {
        /**
         * Платежное поручение
         */
        DOMESTIC,
        /**
         * Налоговый платеж
         */
        DOMESTIC_TAX,
        /**
         * Платежное поручение со списком счетов физических лиц
         */
        LIST_ACCOUNTS,
        /**
         * Платежное поручение со списком физических лиц без открытия счета
         */
        LIST_PASSPORTS,
        /**
         * Платежное требование
         */
        REQUIREMENT,
        /**
         * Платежное требование на уплату налогов (других платежей в бюджет)
         */
        TAX_REQUIREMENT,
        /**
         * Рекуррентный платеж
         */
        VRP
    }

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
    @Enumerated(value = STRING)
    private Type type;

    /**
     * Дата и время создания платежа
     */
    @Column(name = "CREATION_TIME")
    private Date createTime;

    /**
     * Статус платежа
     */
    @Column(name = "STATUS")
    private Status status;

    /**
     * Дата и время обновления статуса
     */
    @Column(name = "STATUS_UPDATE_TIME")
    private Date statusUpdateTime;

    public PaymentEntity() {
    }

}
