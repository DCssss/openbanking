package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.Client;
import by.openbanking.openbankingservice.repository.ClientRepository;
import by.openbanking.openbankingservice.util.StubData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository mClientRepository;

    public Client findClient(
            final String apiKey
    ) {
        final Long clientId = StubData.CLIENTS.get(apiKey);
        if (clientId != null) {
            return mClientRepository.getById(clientId);
        } else {
            throw new RuntimeException("Illegal ApiKey");
        }
    }
}
