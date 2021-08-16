package openbankingservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@Data
@AllArgsConstructor
public final class OBHttpHeaders {

    public static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    public static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    public static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    public static final String AUTHORIZATION = "authorization";
    public static final String X_API_KEY = "x-api-key";
    public static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";
    public static final String DOMESTIC_CONSENT_ID = "domesticConsentId";
    public static final String DOMESTIC_TAX_CONSENT_ID = "domesticTaxConsentId";
    public static final String PAYMENT_CONSENT_ID = "paymentConsentId";
    public static final String LIST_ACCOUNT_CONSENT_ID = "listAccountsConsentId";
    public static final String X_IDEMPOTENCY_KEY = "x-idempotency-key";
    public static final String X_JWS_SIGNATURE = "x-jws-signature";
    public static final String X_CUSTOMER_USER_AGENT = "x-customer-user-agent";


    public HttpHeaders getAccInfoHeaders(
            String xFapiInteractionId,
            String authorization,
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xApiKey
    )
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(OBHttpHeaders.AUTHORIZATION, authorization);
        headers.add(OBHttpHeaders.X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(OBHttpHeaders.X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(OBHttpHeaders.X_API_KEY, xApiKey);

        return headers;
    }

    public HttpHeaders getPaymentHeaders(String xIdempotencyKey,
                                          String xJwsSignature,
                                          String xFapiAuthDate,
                                          String xFapiInteractionId,
                                          String authorization,
                                          String xFapiCustomerIpAddress,
                                          String xCustomerUserAgent,
                                          String xApiKey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(OBHttpHeaders.X_IDEMPOTENCY_KEY, xIdempotencyKey);
        headers.add(OBHttpHeaders.X_JWS_SIGNATURE, xJwsSignature);
        headers.add(OBHttpHeaders.AUTHORIZATION, authorization);
        headers.add(OBHttpHeaders.X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(OBHttpHeaders.X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(OBHttpHeaders.X_CUSTOMER_USER_AGENT, xCustomerUserAgent);
        headers.add(OBHttpHeaders.X_API_KEY, xApiKey);
        return headers;
    }

}
