package by.openbanking.openbankingservice.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import by.openbanking.openbankingservice.model.Consent;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

    Collection<Consent> findByClientId(Long clientId);
}