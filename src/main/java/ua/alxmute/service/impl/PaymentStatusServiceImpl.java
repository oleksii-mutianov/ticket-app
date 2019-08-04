package ua.alxmute.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.service.PaymentStatusService;

@Service
@AllArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusService {

    @Override
    public PaymentStatus randomPaymentStatus() {
        return PaymentStatus.randomStatus();
    }
}
