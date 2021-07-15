package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.models.Permission;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public final class RightsController {
    private RightsController() {
    }

    public static boolean isHaveRights(
            final AccountConsentsRepository accountConsentsRepository,
            final Long clientId,
            final String api
    ) {
        //получить все актуальные согласия пользователя
        final Collection<AccountConsents> accountConsentsCollection =
                accountConsentsRepository
                        .findByClientId(clientId)
                        .stream()
                        .filter(accountConsents -> accountConsents.getExpirationDate().after(new Date()))
                        .collect(Collectors.toList());
        for (AccountConsents accountConsents : accountConsentsCollection) {
            for (Permission permission : accountConsents.getPermission()) {
                if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                    return true;
                }
            }
        }
        return false;
    }
}
