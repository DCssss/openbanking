package by.openbanking.openbankingservice.repository;

import java.util.List;

import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountsRepository extends JpaRepository<Accounts, Long> {

}