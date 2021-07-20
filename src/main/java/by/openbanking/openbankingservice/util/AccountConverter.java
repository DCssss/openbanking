package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.Account;
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
        acc.setAccountid(String.valueOf(obAccount.getId()));
        acc.setStatus(by.openbanking.openbankingservice.models.Account.StatusEnum.PENDING);
        acc.setStatusUpdateTime(obAccount.getStatusUpdateTime());
        acc.setCurrency(obAccount.getCurrency());
        acc.setAccountType(obAccount.getType());
        acc.setAccountSubType(by.openbanking.openbankingservice.models.Account.AccountSubTypeEnum.LOAN);
        acc.setCreationDataTime(obAccount.getCreationTime());
        acc.setAccountDescription(obAccount.getDescription());
        servicer.setIdentification("ЗАО БСБ Банк");
        servicer.setName("UNBSBY2X");
        accountDetails.setIdentification(obAccount.getIdentification());
        accountDetails.setSubstatus("Arrested");
        accountDetails.setName(obAccount.getName());
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
