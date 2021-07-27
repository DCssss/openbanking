package by.openbanking.openbankingservice.validation;

import by.openbanking.openbankingservice.models.Consent;
import by.openbanking.openbankingservice.models.Permission;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public final class ConsentBodyValidator implements ConstraintValidator<ConsentBody, Consent> {

    @Override
    public boolean isValid(
            final Consent value,
            final ConstraintValidatorContext context
    ) {
        boolean result = true;

        final Collection<Permission> permissions = value.getData().getPermissions();

        if (
                (//в согласии содержится ReadTransactionsBasic или ReadTransactionsDetail, но не содержится ни ReadTransactionsCredits, ни ReadTransactionsDebits;
                        (
                                permissions.contains(Permission.READTRANSACTIONSBASIC)
                                        || permissions.contains(Permission.READTRANSACTIONSDETAIL)
                        )
                                && !(
                                permissions.contains(Permission.READTRANSACTIONSCREDITS)
                                        || permissions.contains(Permission.READTRANSACTIONSDEBITS)
                        )
                )
                        || (//либо в согласии содержится ReadTransactionsCredits или ReadTransactionsDebits, но не содержится ни ReadTransactionsBasic, ни ReadTransactionsDetail;
                        (
                                permissions.contains(Permission.READTRANSACTIONSCREDITS)
                                        || permissions.contains(Permission.READTRANSACTIONSDEBITS)
                        )
                                && !(
                                permissions.contains(Permission.READTRANSACTIONSBASIC)
                                        || permissions.contains(Permission.READTRANSACTIONSDETAIL)
                        )
                )
        ) {
            result = false;
        }
        return result;
    }

}
