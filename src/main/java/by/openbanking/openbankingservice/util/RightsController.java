package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.model.Consent;
import by.openbanking.openbankingservice.models.AccountConsentsStatus;
import by.openbanking.openbankingservice.models.Permission;
import by.openbanking.openbankingservice.repository.ConsentRepository;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public final class RightsController {
    private RightsController() {
    }

    public static boolean isHaveRights(
            final ConsentRepository consentRepository,
            final Long clientId,
            final String api
    ) {
        //получить все актуальные согласия пользователя
        final Collection<Consent> consentCollection =
                consentRepository
                        .findByClientId(clientId)
                        .stream()
                        .filter(consent -> consent.getExpirationDate().after(new Date()))
                        .filter(consent -> consent.getStatus().equals(AccountConsentsStatus.AUTHORISED.toString()))
                        .collect(Collectors.toList());
        for (Consent consent : consentCollection) {
            for (Permission permission : consent.getPermission()) {
                if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                    return true;
                }
            }
        }
        return false;
    }
}
