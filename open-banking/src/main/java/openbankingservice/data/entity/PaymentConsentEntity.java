package openbankingservice.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openbankingservice.models.payments.PaymentConsentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * Платежное указание.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "OB_PAYMENT_CONSENTS")
public final class PaymentConsentEntity extends BaseEntity<Long> {

    /**
     * Типы платежных указаний и платежей
     */
    public enum Type {
        /**
         * Платежное указание на инициирование платежного поручения
         */
        DOMESTIC_CONSENT,
        /**
         * Платежное указание на инициирование налогового платежа
         */
        DOMESTIC_TAX_CONSENT,
        /**
         * Платежное указание на инициирование платежного поручения со списком счетов физических лиц
         */
        LIST_ACCOUNTS_CONSENT,
        /**
         * Платежное указание на инициирование платежного поручения со списком физических лиц без открытия счета
         */
        LIST_PASSPORTS_CONSENT,
        /**
         * Платежное указание на выставление платежного требования
         */
        REQUIREMENT_CONSENT,
        /**
         * Платежное указание на выставление платежного требования на уплату налогов (других платежей в бюджет)
         */
        TAX_REQUIREMENT_CONSENT,
        /**
         * Платежное указание на инициирование рекуррентного платежа
         */
        VRP_CONSENT
    }

    /**
     * Дата и время создания платежного указания
     */
    @Column(name = "CREATION_TIME")
    private Date creationTime;

    /**
     * Статус платежного указания
     */
    @Column(name = "STATUS")
    @Enumerated(value = STRING)
    private PaymentConsentStatus status;

    /**
     * Тип платежного указания
     */
    @Column(name = "TYPE")
    @Enumerated(value = STRING)
    private Type type;

    /**
     * Блок Initiation
     */
    @Column(name = "INITIATION", length = 8192)
    private String initiation;

    /**
     * Блок Risk
     */
    @Column(name = "RISK")
    private String risk;

    /**
     * Тип авторизации
     */
    @Column(name = "AUTHORIZATION_TYPE")
    private String authorizationType;

    /**
     * Дата до которой должна быть сделана авторизация
     */
    @Column(name = "COMPLETION_TIME")
    private Date completionTime;

    /**
     * Линк на согласие
     */
    @Column(name = "CONSENT_LINK")
    private String consentLink;

    /**
     * Дата и время обновления статуса
     */
    @Column(name = "STATUS_UPDATE_TIME")
    private Date statusUpdateTime;

    /**
     * Финтех
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINTECH_ID")
    private FintechEntity fintech;

    /**
     * Клиент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    /**
     * Уникальный идентификатор платежной инструкции
     */
    @Column(name = "INSTRUCTION_ID")
    private String instructionId;

    /**
     * Сквозной идентификатор
     */
    @Column(name = "END_TO_END_ID")
    private String endToEndId;

    /**
     * Местный инструмент
     */
    @Column(name = "LOCAL_INSTRUMENT")
    private String localInstrument;

    /**
     * Дата, в которую денежные средства должны быть списаны со счета(-ов) плательщика
     */
    @Column(name = "REQUESTED_EXECUTION_DATE")
    private Date requestedExecutionDate;

    /**
     * Сумма платежа
     */
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    /**
     * Валюта платежа
     */
    @Column(name = "CURRENCY")
    private String currency;

    /**
     * УНН плательщика
     */
    @Column(name = "DEBTOR_TAX_ID")
    private String debtorTaxId;

    /**
     * Имя плательщика
     */
    @Column(name = "DEBTOR_NAME")
    private String debtorName;

    /**
     * Номер счета плательщика
     */
    @Column(name = "DEBTOR_ACC_ID")
    private String debtorAccId;

    /**
     * Схема нумерации счетов плательщика
     */
    @Column(name = "DEBTOR_ACC_SCHEME")
    private String debtorAccScheme;

    /**
     * Название банка плательщика
     */
    @Column(name = "DEBTOR_AGENT_NAME")
    private String debtorAgentName;

    /**
     * БИК банка плательщика
     */
    @Column(name = "DEBTOR_AGENT_ID")
    private String debtorAgentId;

    /**
     * УНН получателя
     */
    @Column(name = "CREDITOR_TAX_ID")
    private String creditorTaxId;

    /**
     * Имя получателя
     */
    @Column(name = "CREDITRO_NAME")
    private String creditorName;

    /**
     * Номер счета получателя
     */
    @Column(name = "CREDITOR_ACC_ID")
    private String creditorAccId;

    /**
     * Схема нумерации счетов получателя
     */
    @Column(name = "CREDITOR_ACC_SCHEME")
    private String creditorAccScheme;

    /**
     * Название банка получателя
     */
    @Column(name = "CREDITOR_AGENT_NAME")
    private String creditorAgentName;

    /**
     * БИК банка получателя
     */
    @Column(name = "CREDITOR_AGENT_ID")
    private String creditorAgentId;

    public PaymentConsentEntity() {
    }

}
