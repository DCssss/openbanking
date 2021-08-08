package openbankingservice.util;

import openbankingservice.models.accinfo.Permission;

import java.util.*;

public final class StubData {

    public static final Map<String, Long> FINTECHS = new HashMap<>();

    static {
        FINTECHS.put("eyJ4NXQiOiJORFUyTTJRMVpXRTVaalJtTUdOaFkyTTVNelJsWW1VeU1HTTROR0ppWXpFd05qQTVabVl4TnpRd1pUTmpOVFJoWWpnMk4yVmlaRGxqWlRabFpqRTJZdyIsImtpZCI6Ik5EVTJNMlExWldFNVpqUm1NR05oWTJNNU16UmxZbVV5TUdNNE5HSmlZekV3TmpBNVptWXhOelF3WlROak5UUmhZamcyTjJWaVpEbGpaVFpsWmpFMll3X1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJGSU5URUNIQGNhcmJvbi5zdXBlciIsImF1ZCI6Ik9BTm1zdEdXWUhxX1N4VFZkaGZRUzNNcjFvd2EiLCJuYmYiOjE2Mjc5MDkwODAsImF6cCI6Ik9BTm1zdEdXWUhxX1N4VFZkaGZRUzNNcjFvd2EiLCJzY29wZSI6ImFtX2FwcGxpY2F0aW9uX3Njb3BlIGRlZmF1bHQiLCJpc3MiOiJodHRwczpcL1wvcGF5bWVudGFwaS5zdC5ieTo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNjI3OTEyNjgwLCJpYXQiOjE2Mjc5MDkwODAsImp0aSI6ImU5ZDEwMTY3LTFkZjAtNGM3NC05YmI1LTI5YTExNTkyNmRmZCJ9.FwM_XroQu_0c6CYu3UZw6t-D1R66e0MUwY-RjpNDrhkS5VYDf6uL66LxkU1QGSesL_Bb48Knh6KvNcURsKNYcfcrUo14a1Aqpd7fu6JDBJZpLlXlRmvoGQju0x1Qy0yiOwYqO_-z2uXuMQsGTyj2oLfkVhpUnnyK2NZu_XEtrbxS5qFnYORrTXfIVIP3XDoeetJZkp0uicMtH9qfFTPAZMpn1VwYklY4OonKK9F-ULyd0X_wZlV0xbt5N1Fukk0Sdvnp9t0HVPLkelF3loV7gSh_MN_B6nDrw8AOvs5k8KHjq4NL3RdDOJacH_VzS_GlrXyc1sVyMCgpZe5khriqSw", 1L);
        FINTECHS.put("bbb", 2L);
    }

    public static final Map<String, Long> CLIENTS = new HashMap<>();

    static {
        CLIENTS.put("eyJ4NXQiOiJORFUyTTJRMVpXRTVaalJtTUdOaFkyTTVNelJsWW1VeU1HTTROR0ppWXpFd05qQTVabVl4TnpRd1pUTmpOVFJoWWpnMk4yVmlaRGxqWlRabFpqRTJZdyIsImtpZCI6Ik5EVTJNMlExWldFNVpqUm1NR05oWTJNNU16UmxZbVV5TUdNNE5HSmlZekV3TmpBNVptWXhOelF3WlROak5UUmhZamcyTjJWaVpEbGpaVFpsWmpFMll3X1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJDTElFTlQxQGNhcmJvbi5zdXBlciIsImF1ZCI6IlljejdoaFo4VUNLRU1QZk5sQ0dQOXdSQ1hrVWEiLCJuYmYiOjE2Mjc5MDkxOTEsImF6cCI6IlljejdoaFo4VUNLRU1QZk5sQ0dQOXdSQ1hrVWEiLCJzY29wZSI6ImFtX2FwcGxpY2F0aW9uX3Njb3BlIGRlZmF1bHQiLCJpc3MiOiJodHRwczpcL1wvcGF5bWVudGFwaS5zdC5ieTo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNjI3OTEyNzkxLCJpYXQiOjE2Mjc5MDkxOTEsImp0aSI6ImE4NGQ2NGU4LWNlYjItNDM4YS05YmI0LTg3MjYzN2Q1MjZhOCJ9.htDYdjl4dwD_juxzwbFsoy2IFhGWhXbaIkhXoScczRE8geL450vKplIj2LgjMoIToJPrdp3UE-HJ_-X3fDI--HVaAsy3YuhUHHlAIl0ZRLXnbMbGA4M-1Q5NkVKrcSMyZv89GJDO_JYsAuxjRdsgbbxIbrz7VWT7gbLMf_8-ln-qqqYFlNf2WGTRy3UwEw9kcig9-ofDi4biAxlSwY5yHcxM_5mMkhrsLAybMr1rFVK0Mdc6rGyRD58SXn6As0V-C3DNiVheFZIjjT22t9xzjFqif1Ua5i6F_Prkfgh7o-uM6lePJPmrtdIc_G5L9nUhouSwo7l88UJvPR_rBp53HA", 1L);
        CLIENTS.put("eyJ4NXQiOiJORFUyTTJRMVpXRTVaalJtTUdOaFkyTTVNelJsWW1VeU1HTTROR0ppWXpFd05qQTVabVl4TnpRd1pUTmpOVFJoWWpnMk4yVmlaRGxqWlRabFpqRTJZdyIsImtpZCI6Ik5EVTJNMlExWldFNVpqUm1NR05oWTJNNU16UmxZbVV5TUdNNE5HSmlZekV3TmpBNVptWXhOelF3WlROak5UUmhZamcyTjJWaVpEbGpaVFpsWmpFMll3X1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJDTElFTlQyQGNhcmJvbi5zdXBlciIsImF1ZCI6IkhRMFV0QTFYOFhUdjYwZzZ1Q2ZmcWdzck1WUWEiLCJuYmYiOjE2Mjc5MDkyODEsImF6cCI6IkhRMFV0QTFYOFhUdjYwZzZ1Q2ZmcWdzck1WUWEiLCJzY29wZSI6ImFtX2FwcGxpY2F0aW9uX3Njb3BlIGRlZmF1bHQiLCJpc3MiOiJodHRwczpcL1wvcGF5bWVudGFwaS5zdC5ieTo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNjI3OTEyODgxLCJpYXQiOjE2Mjc5MDkyODEsImp0aSI6IjU4MjY1NWZlLWRkYjItNDFiOS05ZDk0LTg3YmY1ODBiNGZlOCJ9.BaBbsMwvWdLOpSrtoy87u4mhbc_SvxnN3A4rc0IkggoWay7eHjHAM_vhzP2T23Qq9DeNhZLWg7jIg_1LiIYFl-n-lDZO_qvGU5fpfVQZbxQ4ZoJ9FR30msVdVlHICwIJ_83m1I38__66g5AH3uXkpfuYCQI5JayJ-q2FVMZbBaR_ApdrrwfjZWXdZOsa9f6p4u-4lBIXSd3q7BqFsg9T9b8Wu9y-wax0TbbQcZQckMn6Zf5s3DUdrqJSpoJqfO1RpUSU_BpLWXZjCblK-7UiXYO65mtYUJOJGYQ9_ZQHfFVtWLvYkmvLBDDW7xUlKo6jpSYlzRAAUQg5rmNqSzcyeA", 2L);
    }

    public static final Map<Permission, Collection<String>> PERMISSIONS_API = new HashMap<>();

    static {
        PERMISSIONS_API.put(Permission.READACCOUNTSBASIC, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READACCOUNTSDETAIL, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READBALANCES, Arrays.asList("/balances", "/accounts/{accountId}/balances"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSBASIC, Arrays.asList("/statements/{accountId}", "/statements/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSDETAIL, Arrays.asList("/statements/{accountId}", "/statements/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSBASIC, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDETAIL, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSCREDITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDEBITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
    }

    private StubData() {
    }

}
