package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.entity.ConsentEntity;
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
        final Collection<ConsentEntity> consentCollection =
                consentRepository
                        .findByClientId(clientId)
                        .stream()
                        .filter(consent -> consent.getExpirationDate().after(new Date()))
                        .filter(consent -> consent.getStatus().equals(AccountConsentsStatus.AUTHORISED))
                        .collect(Collectors.toList());
        for (ConsentEntity consent : consentCollection) {
            for (Permission permission : consent.getPermission()) {
                if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isHaveRights(
            final ConsentEntity consent,
            final String api
    ) {
        if (consent.getExpirationDate().after(new Date())
                && consent.getStatus() == AccountConsentsStatus.AUTHORISED) {
            for (Permission permission : consent.getPermission()) {
                if (StubData.PERMISSIONS_API.get(permission).contains(api)) {
                    return true;
                }
            }
        }
        return false;
    }
}
