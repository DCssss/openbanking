package by.openbanking.openbankingservice.repository;


import by.openbanking.openbankingservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {

}