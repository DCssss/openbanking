package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.Account;
import by.openbanking.openbankingservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
