package openbankingservice.util;

import openbankingservice.data.entity.TransactionListEntity;
import openbankingservice.models.accinfo.OBSetAccountsTransAction1;

public final class TransactionListConverter {

    private TransactionListConverter() {
    }

    public static OBSetAccountsTransAction1 toOBSetAccountsTransAction1(final TransactionListEntity transactionListEntity) {
        return new OBSetAccountsTransAction1()
                .transactionListId(transactionListEntity.getId().toString())
                .accountId(transactionListEntity.getAccount().getId().toString())
                .fromBookingDateTime(transactionListEntity.getFromBookingTime())
                .toBookingDateTime((transactionListEntity.getToBookingTime()));
    }
}
