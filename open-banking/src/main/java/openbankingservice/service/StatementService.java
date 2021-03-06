package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.AccountEntity;
import openbankingservice.data.entity.ConsentEntity;
import openbankingservice.data.entity.StatementEntity;
import openbankingservice.data.repository.StatementRepository;
import openbankingservice.data.repository.TransactionRepository;
import openbankingservice.models.accinfo.*;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.StatementConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class StatementService {

    private final StatementRepository mStatementRepository;
    private final TransactionRepository mTransactionRepository;
    private final ClientService mClientService;
    private final ConsentService mConsentService;

    public ResponseEntity<StatementResponse> setStatement(
            @Valid final StatementRequest body,
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/statements/{accountId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));

        final Date now = new Date();

        final StatementEntity statementEntity = new StatementEntity();
        statementEntity.setAccount(account);
        statementEntity.setCreateTime(now);
        statementEntity.setFromBookingDate(body.getData().getStatement().getFromBookingDate());
        statementEntity.setToBookingDate(body.getData().getStatement().getToBookingDate());
        statementEntity.setTransactions(new HashSet<>(mTransactionRepository.findAllByAccountAndBookingTimeBetween(account, statementEntity.getFromBookingDate(), statementEntity.getToBookingDate())));
        mStatementRepository.save(statementEntity);

        final StatementResponseData data = new StatementResponseData();
        data.setStatement(StatementConverter.toStatementResponseDataStatement(statementEntity));

        final LinksStatementPost links = new LinksStatementPost()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/statements/" + accountId);

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final StatementResponse statementResponse = new StatementResponse()
                .data(data)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);


        return new ResponseEntity<>(statementResponse, headers, HttpStatus.OK);
    }
}