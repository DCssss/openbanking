package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.StatementEntity;
import by.openbanking.openbankingservice.models.StatementResponseDataStatement;

public final class StatementConverter {

    public static StatementResponseDataStatement toStatementResponseDataStatement(final StatementEntity statementEntity) {
        return new StatementResponseDataStatement()
                .statementId(statementEntity.getId().toString())
                .accountId(statementEntity.getAccount().getId().toString())
                .fromBookingDate(statementEntity.getFromBookingDate())
                .toBookingDate(statementEntity.getToBookingDate());
    }

}
