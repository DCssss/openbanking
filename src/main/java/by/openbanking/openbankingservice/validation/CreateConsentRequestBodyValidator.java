package by.openbanking.openbankingservice.validation;

import by.openbanking.openbankingservice.models.ConsentData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateConsentRequestBodyValidator implements ConstraintValidator<CreateConsentRequestBody, ConsentData> {

    @Override
    public boolean isValid(
            final ConsentData value,
            final ConstraintValidatorContext context
    ) {
        boolean result = true;

        if (value.getPermissions().isEmpty()){
            result = false;
        }
        return result;
    }

}
