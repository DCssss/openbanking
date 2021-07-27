package by.openbanking.openbankingservice.util;

import by.openbanking.openbankingservice.models.Permission;

import java.util.*;

public final class StubData {

    public static final Map<String, Long> FINTECHS = new HashMap<>();

    static {
        FINTECHS.put("aaa", 1L);
        FINTECHS.put("bbb", 2L);
    }

    public static final Map<String, Long> CLIENTS = new HashMap<>();

    static {
        CLIENTS.put("eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhcHBsaWNhdGlvbiI6eyJvd25lciI6ImFkbWluIiwidGllclF1b3RhVHlwZSI6bnVsbCwidGllciI6IjEwUGVyTWluIiwibmFtZSI6IkFjY291bnRzU3Vic2NyIiwiaWQiOjIsInV1aWQiOiIzYWNlZjFjZS01OTVkLTRhMjYtYTQzYi05NWUwYTY4MDZmNTEifSwiaXNzIjoiaHR0cHM6XC9cL2xvY2FsaG9zdDo5NDQzXC9vYXV0aDJcL3Rva2VuIiwidGllckluZm8iOnsiQnJvbnplIjp7InRpZXJRdW90YVR5cGUiOiJyZXF1ZXN0Q291bnQiLCJncmFwaFFMTWF4Q29tcGxleGl0eSI6MCwiZ3JhcGhRTE1heERlcHRoIjowLCJzdG9wT25RdW90YVJlYWNoIjp0cnVlLCJzcGlrZUFycmVzdExpbWl0IjowLCJzcGlrZUFycmVzdFVuaXQiOm51bGx9fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOlt7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiQWNjb3VudHMiLCJjb250ZXh0IjoiXC9vcGVuLWJhbmtpbmdcL3YxLjEiLCJwdWJsaXNoZXIiOiJhZG1pbiIsInZlcnNpb24iOiJ2MS4xIiwic3Vic2NyaXB0aW9uVGllciI6IkJyb256ZSJ9LHsic3Vic2NyaWJlclRlbmFudERvbWFpbiI6ImNhcmJvbi5zdXBlciIsIm5hbWUiOiJMaWJyYXJ5IiwiY29udGV4dCI6IlwvbGlicmFyeS1zZXJ2aWNlXC92MS4wIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEuMCIsInN1YnNjcmlwdGlvblRpZXIiOiJCcm9uemUifSx7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiQXBpQWNjb3VudCIsImNvbnRleHQiOiJcL2FjY291bnRDb25zZW50cy1zZXJ2aWNlXC92MS4wIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEuMCIsInN1YnNjcmlwdGlvblRpZXIiOiJCcm9uemUifV0sInBlcm1pdHRlZElQIjoiIiwiaWF0IjoxNjI2MjU0ODc0LCJqdGkiOiIzYzA4NjgxZC1mNTc2LTQ1ZTktYmNhMi1jYzYwMDE5ZGYzODEifQ==.QE2Zre-OqgUWzLKmOQFtoZiYIBWm54F-8kuWtX2IsgfqHTRu_delDZudtmZ8pZzE30ezhB1_fzB_knLNcuRK-j02ILOnMsApCfsxGSHWxeru5kOSV5W91td5RlBzJJ5WzFJJbYzDAPd6Z25PeDccH-HoP2sqlEzIBCT2MFTQCjiCRnk6W0PENUCZMA19WrUjfqDaQro0ziVh9XAQtk1OHE0VmDXPB_vMUJns9A4fat0-pTtwu01lQT2frRoySWAqv1ZJc-gjlCX-v5bfQnqvPQHSUr717VES4c8b-R5uMmM0vcwGLqMuFIWDkOzWx9sPGmevog3ZQQknFVPF1BhyvQ==", 1L);
        CLIENTS.put("eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJQVUJMSUNAY2FyYm9uLnN1cGVyIiwiYXBwbGljYXRpb24iOnsib3duZXIiOiJQVUJMSUMiLCJ0aWVyUXVvdGFUeXBlIjpudWxsLCJ0aWVyIjoiVW5saW1pdGVkIiwibmFtZSI6IkRlZmF1bHRBcHBsaWNhdGlvbiIsImlkIjozLCJ1dWlkIjoiMTA1ZWEyOWYtOTdmZC00Njg5LTg5MzUtMjhkNWUzNWE3ZGVjIn0sImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsInRpZXJJbmZvIjp7fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOltdLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTYyNjI1NTAzMSwianRpIjoiNjU3NjM1ZDgtNDcxMS00M2QyLTliZjUtODNlNzdhYTdkMDgxIn0=.LxliKrOXE2lDrN3BOoV8t3cVgtkdB_1VUKAsvZf6nQSo3Gw3xpnPpoGCmkmRqGHUao-UjNBsrJZZF31DeBZEZ6OVSRSoNlDFAAAx0tlxNRGHdkjy7RGK-1bctxeYuA8ITxiL27EczpsP_3c6EOkvFc7lT1eO3JuqHPl_p75LCBKdlUHAm_NyS833BrzLXvBXaVZexB7QH19cdf7XaROqUVKOfr89o_Rw7GHvn-LPEiqtNGkX9YmYlodIEQ_mh6KFXpNTUT5toPJUbQmourHsm6COBipuM8oTko9FwWt54x0RJulRU6yuySuoyUGMf6QNHCcTAYqkY_k6QpbTe7wdkA==", 2L);
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
