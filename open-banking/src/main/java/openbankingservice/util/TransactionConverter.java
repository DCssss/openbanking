package openbankingservice.util;

import openbankingservice.data.entity.TransactionEntity;
import openbankingservice.models.accinfo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TransactionConverter {

    private TransactionConverter() {
    }

    public static OBTransaction1 toOBTransaction1(final TransactionEntity transactionEntity) {
        OBDebtor1 debtor1 = new OBDebtor1();
        OBDebtorAgent1 obDebtorAgent1 = new OBDebtorAgent1();
        obDebtorAgent1.setIdentification(transactionEntity.getDebitBankIdentification());
        obDebtorAgent1.setName(transactionEntity.getDebitBankName());
        debtor1.setDebtorAgent(obDebtorAgent1);

        OBDebtorAccount1 debtorAccount1 = new OBDebtorAccount1();
        OBCreditor1 creditor1 = new OBCreditor1();
        OBCreditorAgent1 obCreditorAgent1 = new OBCreditorAgent1();
        obCreditorAgent1.setIdentification(transactionEntity.getCreditBankIdentification());
        obCreditorAgent1.setName(transactionEntity.getCreditBankName());
        creditor1.setCreditorAgent(obCreditorAgent1);
        OBCreditorAccount1 creditorAccount1 = new OBCreditorAccount1();
        creditorAccount1.setIdentification(transactionEntity.getCreditAccIdentification());
        creditorAccount1.setName(transactionEntity.getCreditName());
        creditor1.setCreditorAccount(creditorAccount1);
        debtorAccount1.setIdentification(transactionEntity.getDebitAccIdentification());
        debtorAccount1.setName(transactionEntity.getDebitName());
        debtor1.setDebtorAccount(debtorAccount1);
        return new OBTransaction1()
                .transactionId(transactionEntity.getId().toString())
                .creditDebitIndicator(transactionEntity.getCreditDebitIndicator())
                .number(transactionEntity.getNumber())
                .bookingDateTime(transactionEntity.getBookingTime())
                .transactionDetails(transactionEntity.getDetails())
                .amount(transactionEntity.getAmount().toString())
                .currency(transactionEntity.getCurrency())
                .debtor(debtor1)
                .creditor(creditor1)
                ;
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
        obTransactionCashBalance.setBalanceAmount(transactionEntity.getAmount().toString());
        OBBankTransactionCodeStructure1 obBankTransactionCodeStructure1 = new OBBankTransactionCodeStructure1();
        OBTransactionCardInstrument1 obTransactionCardInstrument1 = new OBTransactionCardInstrument1();
        OBActiveChargeAmount obActiveChargeAmount = new OBActiveChargeAmount();
        OBReadDataTransaction6 obReadDataTransaction6 = new OBReadDataTransaction6();
        OBDebtor obDebtor = new OBDebtor();
        OBDebtorAccount obDebtorAccount = new OBDebtorAccount();
        OBDebtorAgent debtorAgent = new OBDebtorAgent();
        OBCreditor obCreditor = new OBCreditor();
        OBCreditorAccount obCreditorAccount = new OBCreditorAccount();
        OBCreditorAgent creditorAgent = new OBCreditorAgent();
        OBCurrencyExchange5 obCurrencyExchange5 = new OBCurrencyExchange5();
        OBMerchantDetails1 obMerchantDetails1 = new OBMerchantDetails1();
        ProprietaryBankTransactionCodeStructure1 proprietaryBankTransactionCodeStructure1 = new ProprietaryBankTransactionCodeStructure1();

        obDebtor.setTaxIdentification(transactionEntity.getDebitTaxIdentification());
        obDebtor.setName(transactionEntity.getDebitName());
        obDebtor.setDebtorAccount(obDebtorAccount);
        obDebtor.setDebtorAgent(debtorAgent);

        obDebtorAccount.setIdentification(transactionEntity.getDebitAccIdentification());
        obDebtorAccount.setName(transactionEntity.getDebitName());
        obDebtorAccount.setSchemeName("BY.NBRB.IBAN");

        obCreditorAccount.setIdentification(transactionEntity.getCreditAccIdentification());
        obCreditorAccount.setName(transactionEntity.getCreditName());
        obCreditorAccount.setSchemeName("BY.NBRB.IBAN");

        debtorAgent.setIdentification(transactionEntity.getDebitBankIdentification());
        debtorAgent.setName(transactionEntity.getDebitBankName());

        obCreditor.setTaxIdentification(transactionEntity.getCreditTaxIdentification());
        obCreditor.setName(transactionEntity.getCreditName());
        obCreditor.setCreditorAgent(creditorAgent);
        obCreditor.setCreditorAccount(obCreditorAccount);

        creditorAgent.setIdentification(transactionEntity.getCreditBankIdentification());
        creditorAgent.setName(transactionEntity.getCreditBankName());

        obActiveChargeAmount.setAmount(String.valueOf(transactionEntity.getAmount().doubleValue() * 0.01));
        obActiveChargeAmount.setCurrency(transactionEntity.getCurrency());

        obBankTransactionCodeStructure1.setCode("ReceivedCreditTransfer");
        obBankTransactionCodeStructure1.setSubCode("DomesticCreditTransfer");

        proprietaryBankTransactionCodeStructure1.setCode("Transger");
        proprietaryBankTransactionCodeStructure1.setIssuer("Bank");

        obTransactionCashBalance.setCreditDebitIndicator(transactionEntity.getAmount().compareTo(BigDecimal.ZERO) > 0 ? OBCreditDebitCode2.CREDIT : OBCreditDebitCode2.DEBIT);
        obTransactionCashBalance.setType(BalanceType.OPENINGAVAILABLE);
        obTransactionCashBalance.setCurrency(transactionEntity.getCurrency());

        obTransactionCardInstrument1.setAuthorisationType(OBTransactionCardInstrument1.AuthorisationTypeEnum.CONSUMERDEVICE);
        obTransactionCardInstrument1.setSchemeName(OBTransactionCardInstrument1.SchemeNameEnum.MASTERCARD);

        return new OBTransaction6()
                .transactionId(transactionEntity.getId().toString())
                .transactionReference(UUID.randomUUID().toString())
                .amount(transactionEntity.getAmount().toString())
                .bookingDateTime(transactionEntity.getBookingTime())
                .valueDate(transactionEntity.getBookingTime())
                .transactionDetails(transactionEntity.getDetails())
                .addressLine("Address Line ex")
                .chargeAmount(obActiveChargeAmount)
                .currency(transactionEntity.getCurrency())
                .balance(obTransactionCashBalance)
                .bankTransactionCode(obBankTransactionCodeStructure1)
                .cardInstrument(obTransactionCardInstrument1)
                .chargeAmount(obActiveChargeAmount)
                .currencyExchange(obCurrencyExchange5)
                .creditor(obCreditor)
                .debtor(obDebtor)
                .merchantDetails(obMerchantDetails1)
                .proprietaryBankTransactionCode(proprietaryBankTransactionCodeStructure1)
                .creditDebitIndicator(transactionEntity.getCreditDebitIndicator())
                .status(OBEntryStatus1Code.BOOKED);
    }

    public static List<OBTransaction6> toOBTransaction6(final Iterable<TransactionEntity> transactions) {
        final List<OBTransaction6> transactionList = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionList.add(toOBTransaction6(transaction));
        }
        return transactionList;
    }

}
