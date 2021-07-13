package by.openbanking.openbankingservice.repository;


import by.openbanking.openbankingservice.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalancesRepository extends JpaRepository<Accounts, Long> {

}