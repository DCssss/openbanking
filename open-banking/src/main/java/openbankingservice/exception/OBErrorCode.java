package openbankingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OBErrorCode {
    BY_NBRB_FIELD_EXPECTED("BY.NBRB.Field.Expected", HttpStatus.BAD_REQUEST),
    BY_NBRB_FIELD_INVALID("BY.NBRB.Field.Invalid", HttpStatus.BAD_REQUEST),
    BY_NBRB_FIELD_INVALID_DATE("BY.NBRB.Field.InvalidDate", HttpStatus.BAD_REQUEST),
    BY_NBRB_FIELD_MISSING("BY.NBRB.Field.Missing", HttpStatus.BAD_REQUEST),
    BY_NBRB_HEADER_INVALID("BY.NBRB.Header.Invalid", HttpStatus.BAD_REQUEST),
    BY_NBRB_HEADER_MISSING("BY.NBRB.Header.Missing", HttpStatus.BAD_REQUEST),
    BY_NBRB_RESOURCE_CONSENT_MISMATCH("BY.NBRB.Resource.ConsentMismatch", HttpStatus.BAD_REQUEST),
    BY_NBRB_RESOURCE_INVALID_CONSENT_STATUS("BY.NBRB.Resource.InvalidConsentStatus", HttpStatus.BAD_REQUEST),
    BY_NBRB_RESOURCE_INVALID_FORMAT("BY.NBRB.Resource.InvalidFormat", HttpStatus.BAD_REQUEST),
    BY_NBRB_RESOURCE_NOTFOUND("BY.NBRB.Resource.NotFound", HttpStatus.BAD_REQUEST),
    BY_NBRB_RESOURCE_NOT_CREATED("BY.NBRB.Resource.NotCreated", HttpStatus.BAD_REQUEST),
    BY_NBRB_RULES_AFTER_CUT_OFF_DATE_TIME("BY.NBRB.Rules.AfterCutOffDateTime", HttpStatus.BAD_REQUEST),
    BY_NBRB_SIGNATURE_INVALID("BY.NBRB.Signature.Invalid", HttpStatus.BAD_REQUEST),
    BY_NBRB_SIGNATURE_INVALID_CLAIM("BY.NBRB.Signature.InvalidClaim", HttpStatus.BAD_REQUEST),
    BY_NBRB_SIGNATURE_MISSING_CLAIM("BY.NBRB.Signature.MissingClaim", HttpStatus.BAD_REQUEST),
    BY_NBRB_SIGNATURE_MALFORMED("BY.NBRB.Signature.Malformed", HttpStatus.BAD_REQUEST),
    BY_NBRB_SIGNATURE_MISSING("BY.NBRB.Signature.Missing", HttpStatus.BAD_REQUEST),
    BY_NBRB_UNSUPPORTED_ACCOUNT_IDENTIFIER("BY.NBRB.Unsupported.AccountIdentifier", HttpStatus.BAD_REQUEST),
    BY_NBRB_UNSUPPORTED_LOCAL_INSTRUMENT("BY.NBRB.Unsupported.LocalInstrument", HttpStatus.BAD_REQUEST),
    BY_NBRB_REAUTHENTICATE("BY.NBRB.Reauthenticate", HttpStatus.FORBIDDEN),
    BY_NBRB_RULES_RESOURCE_ALREADY_EXISTS("BY.NBRB.Rules.ResourceAlreadyExists", HttpStatus.CONFLICT),
    BY_NBRB_UNEXPECTED_ERROR("BY.NBRB.UnexpectedError", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String value;
    private final HttpStatus httpStatus;
}
