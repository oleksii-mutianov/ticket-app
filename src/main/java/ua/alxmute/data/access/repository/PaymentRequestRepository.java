package ua.alxmute.data.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.alxmute.data.access.domain.PaymentRequest;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

}
