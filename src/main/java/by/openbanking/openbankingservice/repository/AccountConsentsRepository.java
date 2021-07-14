package by.openbanking.openbankingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import by.openbanking.openbankingservice.model.AccountConsents;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountConsentsRepository extends JpaRepository<AccountConsents, Long> {

    List<AccountConsents> findByAccountConsentIdContaining(String accountConsentId);

}