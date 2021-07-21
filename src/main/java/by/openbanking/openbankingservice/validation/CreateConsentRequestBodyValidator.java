package by.openbanking.openbankingservice.validation;

import by.openbanking.openbankingservice.models.CreateConsentRequestBodyData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateConsentRequestBodyValidator implements ConstraintValidator<CreateConsentRequestBody, CreateConsentRequestBodyData> {

    @Override
    public boolean isValid(
            final CreateConsentRequestBodyData value,
            final ConstraintValidatorContext context
    ) {
        boolean result = true;

        if (value.getPermissions().isEmpty()){
            result = false;
        }
        return result;
    }

}
