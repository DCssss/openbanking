package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.ConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConsentRepository extends JpaRepository<ConsentEntity, Long> {

    Collection<ConsentEntity> findByClientId(Long clientId);
}