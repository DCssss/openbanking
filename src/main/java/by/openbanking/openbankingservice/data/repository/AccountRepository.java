package by.openbanking.openbankingservice.data.repository;

import by.openbanking.openbankingservice.data.entity.AccountEntity;
import by.openbanking.openbankingservice.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Collection<AccountEntity> findByClient(ClientEntity client);

}