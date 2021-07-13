package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.model.Account;
import by.openbanking.openbankingservice.models.AccountDetails;
import by.openbanking.openbankingservice.models.Servicer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AccountConverter {

    private AccountConverter() {
    }

    public static by.openbanking.openbankingservice.models.Account toAccount(
            final Account obAccount
    ) {

        by.openbanking.openbankingservice.models.Account acc = new by.openbanking.openbankingservice.models.Account();
        Servicer servicer = new Servicer();
        AccountDetails accountDetails = new AccountDetails();
        acc.setAccountid(String.valueOf(obAccount.getAccountId()));
        acc.setStatus(by.openbanking.openbankingservice.models.Account.StatusEnum.PENDING);
        acc.setStatusUpdateTime(obAccount.getAccountStatusUpdateTime());
        acc.setCurrency(obAccount.getAccountCurrency());
        acc.setAccountType(obAccount.getAccountType());
        acc.setAccountSubType(by.openbanking.openbankingservice.models.Account.AccountSubTypeEnum.LOAN);
        acc.setCreationDataTime(obAccount.getAccountCreationTime());
        acc.setAccountDescription(obAccount.getAccountDescription());
        servicer.setIdentification("ЗАО БСБ Банк");
        servicer.setName("UNBSBY2X");
        accountDetails.setIdentification(obAccount.getAccountIdentification());
        accountDetails.setSubstatus("Arrested");
        accountDetails.setName(obAccount.getAccountName());
        accountDetails.setReason("Арестован согласно Постановления СК");
        accountDetails.setSchemeName("Схема для осуществления платежа по номеру счета");
        acc.setAccountDetails(accountDetails);
        acc.setServicer(servicer);

        return acc;
    }

    public static List<by.openbanking.openbankingservice.models.Account> toAccount(
            final Collection<Account> obAccounts
    ) {
        final List<by.openbanking.openbankingservice.models.Account> accounts = new ArrayList<>();
        for (Account account : obAccounts) {
            accounts.add(toAccount(account));
        }
        return accounts;
    }
}
