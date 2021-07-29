package by.openbanking.openbankingservice.data.repository;

import by.openbanking.openbankingservice.data.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalancesRepository extends JpaRepository<AccountEntity, Long> {

}