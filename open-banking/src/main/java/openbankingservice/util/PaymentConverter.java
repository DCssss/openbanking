package openbankingservice.util;

import openbankingservice.data.entity.PaymentEntity;
import openbankingservice.models.payments.OBDataDomesticTaxPayment;

public final class PaymentConverter {

    private PaymentConverter() {
    }

    public static PaymentEntity toPaymentEntity(final OBDataDomesticTaxPayment data) {
        final PaymentEntity paymentEntity = new PaymentEntity();
        return paymentEntity;
    }
}
