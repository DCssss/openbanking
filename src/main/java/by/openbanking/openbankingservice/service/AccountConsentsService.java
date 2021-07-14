package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Data;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountConsentsService {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final Map<String, Long> CLIENTS = new HashMap<>();

    private final AccountConsentsRepository mRepository;

    static {
        CLIENTS.put("eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhcHBsaWNhdGlvbiI6eyJvd25lciI6ImFkbWluIiwidGllclF1b3RhVHlwZSI6bnVsbCwidGllciI6IjEwUGVyTWluIiwibmFtZSI6IkFjY291bnRzU3Vic2NyIiwiaWQiOjIsInV1aWQiOiIzYWNlZjFjZS01OTVkLTRhMjYtYTQzYi05NWUwYTY4MDZmNTEifSwiaXNzIjoiaHR0cHM6XC9cL2xvY2FsaG9zdDo5NDQzXC9vYXV0aDJcL3Rva2VuIiwidGllckluZm8iOnsiQnJvbnplIjp7InRpZXJRdW90YVR5cGUiOiJyZXF1ZXN0Q291bnQiLCJncmFwaFFMTWF4Q29tcGxleGl0eSI6MCwiZ3JhcGhRTE1heERlcHRoIjowLCJzdG9wT25RdW90YVJlYWNoIjp0cnVlLCJzcGlrZUFycmVzdExpbWl0IjowLCJzcGlrZUFycmVzdFVuaXQiOm51bGx9fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOlt7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiQWNjb3VudHMiLCJjb250ZXh0IjoiXC9vcGVuLWJhbmtpbmdcL3YxLjEiLCJwdWJsaXNoZXIiOiJhZG1pbiIsInZlcnNpb24iOiJ2MS4xIiwic3Vic2NyaXB0aW9uVGllciI6IkJyb256ZSJ9LHsic3Vic2NyaWJlclRlbmFudERvbWFpbiI6ImNhcmJvbi5zdXBlciIsIm5hbWUiOiJMaWJyYXJ5IiwiY29udGV4dCI6IlwvbGlicmFyeS1zZXJ2aWNlXC92MS4wIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEuMCIsInN1YnNjcmlwdGlvblRpZXIiOiJCcm9uemUifSx7InN1YnNjcmliZXJUZW5hbnREb21haW4iOiJjYXJib24uc3VwZXIiLCJuYW1lIjoiQXBpQWNjb3VudCIsImNvbnRleHQiOiJcL2FjY291bnRDb25zZW50cy1zZXJ2aWNlXC92MS4wIiwicHVibGlzaGVyIjoiYWRtaW4iLCJ2ZXJzaW9uIjoidjEuMCIsInN1YnNjcmlwdGlvblRpZXIiOiJCcm9uemUifV0sInBlcm1pdHRlZElQIjoiIiwiaWF0IjoxNjI2MjU0ODc0LCJqdGkiOiIzYzA4NjgxZC1mNTc2LTQ1ZTktYmNhMi1jYzYwMDE5ZGYzODEifQ==.QE2Zre-OqgUWzLKmOQFtoZiYIBWm54F-8kuWtX2IsgfqHTRu_delDZudtmZ8pZzE30ezhB1_fzB_knLNcuRK-j02ILOnMsApCfsxGSHWxeru5kOSV5W91td5RlBzJJ5WzFJJbYzDAPd6Z25PeDccH-HoP2sqlEzIBCT2MFTQCjiCRnk6W0PENUCZMA19WrUjfqDaQro0ziVh9XAQtk1OHE0VmDXPB_vMUJns9A4fat0-pTtwu01lQT2frRoySWAqv1ZJc-gjlCX-v5bfQnqvPQHSUr717VES4c8b-R5uMmM0vcwGLqMuFIWDkOzWx9sPGmevog3ZQQknFVPF1BhyvQ==", 1L);
        CLIENTS.put("eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkE9PSIsImtpZCI6ImdhdGV3YXlfY2VydGlmaWNhdGVfYWxpYXMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJQVUJMSUNAY2FyYm9uLnN1cGVyIiwiYXBwbGljYXRpb24iOnsib3duZXIiOiJQVUJMSUMiLCJ0aWVyUXVvdGFUeXBlIjpudWxsLCJ0aWVyIjoiVW5saW1pdGVkIiwibmFtZSI6IkRlZmF1bHRBcHBsaWNhdGlvbiIsImlkIjozLCJ1dWlkIjoiMTA1ZWEyOWYtOTdmZC00Njg5LTg5MzUtMjhkNWUzNWE3ZGVjIn0sImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsInRpZXJJbmZvIjp7fSwia2V5dHlwZSI6IlBST0RVQ1RJT04iLCJwZXJtaXR0ZWRSZWZlcmVyIjoiIiwic3Vic2NyaWJlZEFQSXMiOltdLCJwZXJtaXR0ZWRJUCI6IiIsImlhdCI6MTYyNjI1NTAzMSwianRpIjoiNjU3NjM1ZDgtNDcxMS00M2QyLTliZjUtODNlNzdhYTdkMDgxIn0=.LxliKrOXE2lDrN3BOoV8t3cVgtkdB_1VUKAsvZf6nQSo3Gw3xpnPpoGCmkmRqGHUao-UjNBsrJZZF31DeBZEZ6OVSRSoNlDFAAAx0tlxNRGHdkjy7RGK-1bctxeYuA8ITxiL27EczpsP_3c6EOkvFc7lT1eO3JuqHPl_p75LCBKdlUHAm_NyS833BrzLXvBXaVZexB7QH19cdf7XaROqUVKOfr89o_Rw7GHvn-LPEiqtNGkX9YmYlodIEQ_mh6KFXpNTUT5toPJUbQmourHsm6COBipuM8oTko9FwWt54x0RJulRU6yuySuoyUGMf6QNHCcTAYqkY_k6QpbTe7wdkA==", 2L);
    }

    @Autowired
    public AccountConsentsService(
            final AccountConsentsRepository repository
    ) {
        mRepository = repository;
    }

    public ResponseEntity<Void> authorizeAccountConsents(
            final String accountConsentId,
            final String xFapiInteractionId,
            final String apikey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final Long clientId = CLIENTS.get(apikey);

        if (clientId != null) {

            final Optional<AccountConsents> accountConsentsOptional = mRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {

                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setClientId(clientId);
                accountConsents.setAccountConsentStatus(OBReadConsentResponse1Data.StatusEnum.AUTHORISED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mRepository.save(accountConsents);
                response = new ResponseEntity<>(headers, HttpStatus.OK);

            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<OBReadConsentResponse1Post> createAccountAccessConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1Post> response;
        try {
            final AccountConsents accountConsents = AccountConsents.valueOf(body.getData());
            accountConsents.setAccountConsentStatus(OBReadConsentResponse1Data.StatusEnum.AWAITINGAUTHORISATION.toString());

            final Date now = new Date();
            accountConsents.setStatusUpdateTime(now);
            accountConsents.setCreationTime(now);
            mRepository.save(accountConsents);

            final OBReadConsentResponse1Post responseContent = new OBReadConsentResponse1Post();
            responseContent.setData(accountConsents.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<Void> deleteAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        try {
            final Optional<AccountConsents> accountConsentsOptional = mRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {
                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setAccountConsentStatus(OBReadConsentResponse1Data.StatusEnum.REVOKED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mRepository.save(accountConsents);

                response = new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<OBReadConsentResponse1> getAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiInteractionId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1> response;

        final Optional<AccountConsents> accountConsentsData = mRepository.findById(Long.valueOf(accountConsentId));

        if (accountConsentsData.isPresent()) {
            final OBReadConsentResponse1 obReadConsentResponse1 = new OBReadConsentResponse1();
            obReadConsentResponse1.setData(accountConsentsData.get().toOBReadConsentResponse1Data());

            response = new ResponseEntity<>(obReadConsentResponse1, headers, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        return response;
    }
}
