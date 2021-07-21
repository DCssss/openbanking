package by.openbanking.openbankingservice.filter;

import by.openbanking.openbankingservice.models.Permission;
import by.openbanking.openbankingservice.repository.ClientRepository;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public final class ApiConsentFilter implements Filter {

    public static final Map<Permission, Collection<String>> PERMISSIONS_API = new HashMap<>();
    public static final Map<String, Collection<Permission>> API_PERMISSIONS = new HashMap<>();

    static {
        PERMISSIONS_API.put(Permission.READACCOUNTSBASIC, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READACCOUNTSDETAIL, Arrays.asList("/accounts", "/accounts/{accountId}"));
        PERMISSIONS_API.put(Permission.READBALANCES, Arrays.asList("/balances", "/accounts/{accountId}/balances"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSBASIC, Collections.singletonList("/statements/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READSTATEMENTSDETAIL, Collections.singletonList("/statements/accounts/{accountId}/statements/{statementId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSBASIC, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDETAIL, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSCREDITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));
        PERMISSIONS_API.put(Permission.READTRANSACTIONSDEBITS, Collections.singletonList("/accounts/{accountId}/transactions/{transactionListId}"));

        API_PERMISSIONS.put("/accounts", Arrays.asList(Permission.READACCOUNTSBASIC, Permission.READACCOUNTSDETAIL));
        API_PERMISSIONS.put("/accounts/{accountId}", Arrays.asList(Permission.READACCOUNTSBASIC, Permission.READACCOUNTSDETAIL));
    }

    private final ClientRepository mClientRepository;

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        //final String api = ((RequestFacade)request).getRequestURI();
        //final String apiKey = ((RequestFacade)request).getHe
        chain.doFilter(request, response);
    }
}
