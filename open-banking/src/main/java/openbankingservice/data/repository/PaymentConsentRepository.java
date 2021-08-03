package openbankingservice.data.repository;

import openbankingservice.data.entity.PaymentConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentConsentRepository extends JpaRepository<PaymentConsentEntity, Long> {
}
