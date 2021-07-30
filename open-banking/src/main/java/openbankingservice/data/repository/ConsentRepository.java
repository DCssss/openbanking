package openbankingservice.data.repository;

import openbankingservice.data.entity.ConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConsentRepository extends JpaRepository<ConsentEntity, Long> {

    Collection<ConsentEntity> findByClientId(Long clientId);
}