package openbankingservice.data.repository;

import openbankingservice.data.entity.PaymentConsentEntity;
import openbankingservice.models.payments.StatusPaymentConsent;
import openbankingservice.models.payments.TypePaymentConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentConsentRepository extends JpaRepository<PaymentConsentEntity, Long> {

    @Query("SELECT pc " +
            "FROM PaymentConsentEntity pc " +
            "WHERE pc.creationTime BETWEEN :fromCreationTime AND :toCreationTime " +
            "AND (:type IS NULL OR pc.type = :type) " +
            "AND (:status IS NULL OR pc.status = :status)")
    List<PaymentConsentEntity> findAllByCreationTimeBetweenAndTypeAndStatus(
            final Date fromCreationTime,
            final Date toCreationTime,
            final TypePaymentConsent type,
            final StatusPaymentConsent status
    );

}
