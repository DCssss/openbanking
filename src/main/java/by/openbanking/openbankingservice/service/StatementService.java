package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.Statement;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementService {
    private static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    private static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final String AUTHORIZATION = "authorization";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";

    private final AccountRepository mAccountRepository;
    private final StatementRepository mStatementRepository;

    public ResponseEntity<OBReadStatement2Post> setStatement(
            @Valid final OBSetStatement body,
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);

        ResponseEntity<OBReadStatement2Post> response;

        final Date now = new Date();

        Statement statement = new Statement();
        statement.setAccount(mAccountRepository.getById(Long.valueOf(accountId)));
        statement.setCreateTime(now);
        statement.setFromBookingDate(body.getData().getStatement().getFromBookingDate());
        statement.setToBookingDate(body.getData().getStatement().getToBookingDate());
        mStatementRepository.save(statement);

        final LinksStatementPost links = new LinksStatementPost();

        links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/statements/" + accountId);
        final Meta meta = new Meta();
        meta.setTotalPages(1);
        meta.setFirstAvailableDateTime(now);
        meta.setLastAvailableDateTime(now);

        final Statement statement1 = mStatementRepository.findStatementsById(accountId);


        if (StringUtils.isNotBlank(accountId)) {
            final OBReadStatement2Post responseContent = new OBReadStatement2Post();
            final OBReadDataStatement2Post obReadDataStatement2Post = new OBReadDataStatement2Post();
            final OBStatement2Post obStatement2Post = new OBStatement2Post();
            final List<OBStatement2Post> listStatements = new ArrayList<>();

            obStatement2Post.setStatementId(String.valueOf(statement1.getId()));
            obStatement2Post.setAccountId(String.valueOf(statement1.getAccount().getId()));
            obStatement2Post.setFromBookingDate(statement1.getFromBookingDate());
            obStatement2Post.setToBookingDate(statement1.getToBookingDate());
            listStatements.add(obStatement2Post);
            obReadDataStatement2Post.setStatement(listStatements);
            responseContent.setData(obReadDataStatement2Post);
            responseContent.setLinks(links);
            responseContent.setMeta(meta);
            response = new ResponseEntity<>(responseContent, headers, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return response;
    }
}