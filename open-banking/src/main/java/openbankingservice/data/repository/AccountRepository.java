package openbankingservice.data.repository;

import openbankingservice.data.entity.AccountEntity;
import openbankingservice.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Collection<AccountEntity> findByClient(final ClientEntity client);

    AccountEntity getByIdentification(final String identification);
}