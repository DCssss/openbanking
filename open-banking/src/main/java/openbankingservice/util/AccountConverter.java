package openbankingservice.util;

import openbankingservice.data.entity.AccountEntity;
import openbankingservice.models.accinfo.Account;
import openbankingservice.models.accinfo.AccountDetails;
import openbankingservice.models.accinfo.Servicer;

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
        acc.setStatus(Account.StatusEnum.PENDING);
        acc.setStatusUpdateTime(obAccount.getStatusUpdateTime());
        acc.setCurrency(obAccount.getCurrency());
        acc.setAccountType(obAccount.getType());
        acc.setAccountSubType(Account.AccountSubTypeEnum.LOAN);
        acc.setCreationDataTime(obAccount.getCreationTime());
        acc.setAccountDescription(obAccount.getDescription());

        final Servicer servicer = new Servicer();
        servicer.setIdentification(obAccount.getClient().getBank().getIdentifier());
        servicer.setName(obAccount.getClient().getBank().getName());

        acc.setServicer(servicer);

        if (withDetail) {
            final AccountDetails accountDetails = new AccountDetails();
            accountDetails.setIdentification(obAccount.getIdentification());
            accountDetails.setSubstatus(obAccount.getStatus().toString());
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
