package openbankingservice.util;

import openbankingservice.models.accinfo.Permission;

import java.util.*;

public final class StubData {

    public static final Map<String, Long> FINTECHS = new HashMap<>();

    static {
        FINTECHS.put("Bearer eyJ4NXQiOiJORFUyTTJRMVpXRTVaalJtTUdOaFkyTTVNelJsWW1VeU1HTTROR0ppWXpFd05qQTVabVl4TnpRd1pUTmpOVFJoWWpnMk4yVmlaRGxqWlRabFpqRTJZdyIsImtpZCI6Ik5EVTJNMlExWldFNVpqUm1NR05oWTJNNU16UmxZbVV5TUdNNE5HSmlZekV3TmpBNVptWXhOelF3WlROak5UUmhZamcyTjJWaVpEbGpaVFpsWmpFMll3X1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJGSU5URUNIQGNhcmJvbi5zdXBlciIsImF1ZCI6Ik9BTm1zdEdXWUhxX1N4VFZkaGZRUzNNcjFvd2EiLCJuYmYiOjE2Mjc5MDkwODAsImF6cCI6Ik9BTm1zdEdXWUhxX1N4VFZkaGZRUzNNcjFvd2EiLCJzY29wZSI6ImFtX2FwcGxpY2F0aW9uX3Njb3BlIGRlZmF1bHQiLCJpc3MiOiJodHRwczpcL1wvcGF5bWVudGFwaS5zdC5ieTo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNjI3OTEyNjgwLCJpYXQiOjE2Mjc5MDkwODAsImp0aSI6ImU5ZDEwMTY3LTFkZjAtNGM3NC05YmI1LTI5YTExNTkyNmRmZCJ9.FwM_XroQu_0c6CYu3UZw6t-D1R66e0MUwY-RjpNDrhkS5VYDf6uL66LxkU1QGSesL_Bb48Knh6KvNcURsKNYcfcrUo14a1Aqpd7fu6JDBJZpLlXlRmvoGQju0x1Qy0yiOwYqO_-z2uXuMQsGTyj2oLfkVhpUnnyK2NZu_XEtrbxS5qFnYORrTXfIVIP3XDoeetJZkp0uicMtH9qfFTPAZMpn1VwYklY4OonKK9F-ULyd0X_wZlV0xbt5N1Fukk0Sdvnp9t0HVPLkelF3loV7gSh_MN_B6nDrw8AOvs5k8KHjq4NL3RdDOJacH_VzS_GlrXyc1sVyMCgpZe5khriqSw", 1L);
        FINTECHS.put("bbb", 2L);
    }

    public static final Map<String, Long> CLIENTS = new HashMap<>();

    static {
        CLIENTS.put("eyJ4NXQiOiJNVGcyWldVellXTTNNMll5TXpZM1pEUXlZV0l6TlRFeFpUazVZelZrWmpVeFpXUTFNR1UxWmc9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJDTElFTlQxQGNhcmJvbi5zdXBlciIsImFwcGxpY2F0aW9uIjp7Im93bmVyIjoiQ0xJRU5UMSIsInRpZXJRdW90YVR5cGUiOm51bGwsInRpZXIiOiJVbmxpbWl0ZWQiLCJuYW1lIjoiRGVmYXVsdEFwcGxpY2F0aW9uIiwiaWQiOjQsInV1aWQiOiI5M2VjNGYxZC05ZDQ4LTQ1NmUtYmIxYS01YTdjZDc1ODNlMjUifSwiaXNzIjoiaHR0cHM6XC9cL3BheW1lbnRhcGkuc3QuYnk6OTQ0M1wvb2F1dGgyXC90b2tlbiIsInRpZXJJbmZvIjp7fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOltdLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTYyNzkwOTIyNiwianRpIjoiZDExMGMzNmMtMTI5MS00YTMxLTkyNDQtNTg0ZjVjZTk1YmU4In0=.kLaWeUgt6tl4WPI8nCdQX96Y-2GOFrruuo7sI0hu17oM03wJ3g8qtNzQX0PYiKL2A6Ahn5eqh65kukdoLFsugHQUydR9Yvoj8choEsgTIqXEFKrkglOHgO5xmiZuwClsd1zGCWfDDTAPI5zaQV-WLHrQjEmhJOiUaAhfS09uad-mNWpmbnrcIRCkup242rnJILDGI6EvQSg8QwjsISkEl1GNtrFQmGSKdAZqzz6lnrSYPjS0tC-C93dtkSMuUZRMUrY-b0B7JXeQNDLHPjR8VCDneiuxJpbLOo7aOHQWCKcShFWLGUGEjVb3blUOzKXzpHPCjsRKJjke2YoOVeBeKg==", 1L);
        CLIENTS.put("eyJ4NXQiOiJNVGcyWldVellXTTNNMll5TXpZM1pEUXlZV0l6TlRFeFpUazVZelZrWmpVeFpXUTFNR1UxWmc9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJDTElFTlQyQGNhcmJvbi5zdXBlciIsImFwcGxpY2F0aW9uIjp7Im93bmVyIjoiQ0xJRU5UMiIsInRpZXJRdW90YVR5cGUiOm51bGwsInRpZXIiOiJVbmxpbWl0ZWQiLCJuYW1lIjoiRGVmYXVsdEFwcGxpY2F0aW9uIiwiaWQiOjUsInV1aWQiOiJkNGQ3YzIxMi03ZTdkLTQwY2UtYmYyNS1lYWNiZmE3NjBhYzYifSwiaXNzIjoiaHR0cHM6XC9cL3BheW1lbnRhcGkuc3QuYnk6OTQ0M1wvb2F1dGgyXC90b2tlbiIsInRpZXJJbmZvIjp7fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOltdLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTYyNzkwOTMwMiwianRpIjoiODIxMzc2ZTQtNjNjOS00YWRlLWFlNDUtYjQ1Yzc2NmQyZGI1In0=.kpqcUPDGwVCSmlmmHflVg3ANT8w80Eq-U0HTJ2hwIWRZu7TZAodPkTw-FSylKX5U5W0VoFjN7PV9HRXjZU8ume_Adl_s6Dyqa5XvOViUjEBtF26-cjZ5CCNbiEGvAb5yuk7u6zQRIxphRzOOazg-C2l2Qwf2F41w4_Lo0CkI5dWXNl3M9Dvcgnmf6lO6df68mVOidFv7ITr53gjwMcVmc9Rert2-TH5nwkIFDoNv3W_IvJrufRAd9c9Lj-Urip6qPUzgDsEl1I27af5XY18SXyjXBgx6aP0Fl0dO1FPjbSGYTMNH57Ctz-xu2Dyxg_jV9DGEqKsMfc1qR_vdEVZvpA==", 2L);
    }

    public static final Map<Permission, Collection<String>> PERMISSIONS_API = new HashMap<>();

    static {
        PERMISSIONS_API.put(Permission.READACCOUNTSBASIC, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READACCOUNTSDETAIL, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READBALANCES, Arrays.asList("/balances", "/accounts/{accountId}/balances"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSBASIC, Arrays.asList("/statements/{accountId}", "/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSDETAIL, Arrays.asList("/statements/{accountId}", "/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSBASIC, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDETAIL, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSCREDITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDEBITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
    }

    private StubData() {
    }

}
