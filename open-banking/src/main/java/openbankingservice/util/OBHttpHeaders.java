package openbankingservice.util;

public final class OBHttpHeaders {

    public static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    public static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    public static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    public static final String AUTHORIZATION = "authorization";
    public static final String X_API_KEY = "x-api-key";
    public static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";
    public static final String DOMESTIC_CONSENT_ID = "domesticConsentId";
    public static final String X_IDEMPOTENCY_KEY = "x-idempotency-key";
    public static final String X_JWS_SIGNATURE = "x-jws-signature";
    public static final String X_CUSTOMER_USER_AGENT = "x-customer-user-agent";



    private OBHttpHeaders() {
    }
}
