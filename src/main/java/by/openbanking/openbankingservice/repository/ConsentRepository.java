package by.openbanking.openbankingservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import by.openbanking.openbankingservice.entity.ConsentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<ConsentEntity, Long> {

    Collection<ConsentEntity> findByClientId(Long clientId);
}