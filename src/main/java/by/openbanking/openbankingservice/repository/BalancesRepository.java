package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalancesRepository extends JpaRepository<Account, Long> {

}