package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.OBAccounts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountsRepository extends JpaRepository<OBAccounts, Long> {

}