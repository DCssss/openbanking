package openbankingservice.data.repository;

import openbankingservice.data.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
    boolean existsByIdentifier(final String identifier);
}
