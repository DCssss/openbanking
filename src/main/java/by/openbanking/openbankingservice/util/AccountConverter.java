package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.AccountEntity;
import by.openbanking.openbankingservice.models.Account;
import by.openbanking.openbankingservice.models.AccountDetails;
import by.openbanking.openbankingservice.models.Servicer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AccountConverter {

    private AccountConverter() {
    }

    public static Account toAccount(
            final AccountEntity obAccount,
            final boolean withDetail
    ) {
        final Account acc = new Account();
        acc.setAccountid(String.valueOf(obAccount.getId()));
        acc.setStatus(by.openbanking.openbankingservice.models.Account.StatusEnum.PENDING);
        acc.setStatusUpdateTime(obAccount.getStatusUpdateTime());
        acc.setCurrency(obAccount.getCurrency());
        acc.setAccountType(obAccount.getType());
        acc.setAccountSubType(by.openbanking.openbankingservice.models.Account.AccountSubTypeEnum.LOAN);
        acc.setCreationDataTime(obAccount.getCreationTime());
        acc.setAccountDescription(obAccount.getDescription());

        final Servicer servicer = new Servicer();
        servicer.setIdentification("ЗАО БСБ Банк");
        servicer.setName("UNBSBY2X");

        acc.setServicer(servicer);

        if (withDetail) {
            final AccountDetails accountDetails = new AccountDetails();
            accountDetails.setIdentification(obAccount.getIdentification());
            accountDetails.setSubstatus("Arrested");
            accountDetails.setName(obAccount.getName());
            accountDetails.setReason("Арестован согласно Постановления СК");
            accountDetails.setSchemeName("Схема для осуществления платежа по номеру счета");

            acc.setAccountDetails(accountDetails);
        }

        return acc;
    }

    public static List<Account> toAccount(
            final Collection<AccountEntity> obAccounts,
            final boolean withDetail
    ) {
        final List<Account> accounts = new ArrayList<>();
        for (AccountEntity account : obAccounts) {
            accounts.add(toAccount(account, withDetail));
        }
        return accounts;
    }
}
