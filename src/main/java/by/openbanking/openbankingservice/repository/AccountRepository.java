package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.Account;
import by.openbanking.openbankingservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Collection<Account> findByClient(Client client);

}