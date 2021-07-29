package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.TransactionEntity;
import by.openbanking.openbankingservice.models.accinfo.*;

import java.util.ArrayList;
import java.util.List;

public final class TransactionConverter {

    private TransactionConverter() {
    }

    public static OBTransaction1 toOBTransaction1(final TransactionEntity transactionEntity) {
        return new OBTransaction1()
                .transactionId(transactionEntity.getId().toString())
                .creditDebitIndicator(transactionEntity.getCreditDebitIndicator())
                .number(transactionEntity.getNumber())
                .bookingDateTime(transactionEntity.getBookingTime())
                .transactionDetails(transactionEntity.getDetails())
                .amount(transactionEntity.getAmount().toString())
                .currency(transactionEntity.getCurrency());
    }

    public static List<OBTransaction1> toOBTransaction1(final Iterable<TransactionEntity> transactions) {
        final List<OBTransaction1> transactionList = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionList.add(toOBTransaction1(transaction));
        }
        return transactionList;
    }

    public static OBTransaction6 toOBTransaction6(final TransactionEntity transactionEntity) {
        // TODO: 26.07.2021 Доделать конвертацию
        OBTransactionCashBalance obTransactionCashBalance = new OBTransactionCashBalance();
        OBBankTransactionCodeStructure1 obBankTransactionCodeStructure1 = new OBBankTransactionCodeStructure1();
        OBTransactionCardInstrument1 obTransactionCardInstrument1 = new OBTransactionCardInstrument1();
        OBActiveChargeAmount obActiveChargeAmount = new OBActiveChargeAmount();
        OBCreditor obCreditor = new OBCreditor();
        OBCurrencyExchange5 obCurrencyExchange5 = new OBCurrencyExchange5();
        OBMerchantDetails1 obMerchantDetails1 = new OBMerchantDetails1();
        ProprietaryBankTransactionCodeStructure1 proprietaryBankTransactionCodeStructure1 = new ProprietaryBankTransactionCodeStructure1();

        return new OBTransaction6()
                .transactionId(transactionEntity.getId().toString())
                .balance(obTransactionCashBalance)
                .bankTransactionCode(obBankTransactionCodeStructure1)
                .cardInstrument(obTransactionCardInstrument1)
                .chargeAmount(obActiveChargeAmount)
                .currencyExchange(obCurrencyExchange5)
                .creditor(obCreditor)
                .merchantDetails(obMerchantDetails1)
                .proprietaryBankTransactionCode(proprietaryBankTransactionCodeStructure1)
                .creditDebitIndicator(OBCreditDebitCode1.CREDIT)
                .status(OBEntryStatus1Code.PENDING);
    }

    public static List<OBTransaction6> toOBTransaction6(final Iterable<TransactionEntity> transactions) {
        final List<OBTransaction6> transactionList = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionList.add(toOBTransaction6(transaction));
        }
        return transactionList;
    }

}
