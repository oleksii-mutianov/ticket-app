package ua.alxmute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.service.PaymentStatusService;

@RestController
@RequiredArgsConstructor
@RequestMapping("status")
public class StatusController {

    private final PaymentStatusService paymentStatusService;

    @GetMapping
    public ResponseEntity<PaymentStatus> getRandomStatus() {
        return ResponseEntity.ok(paymentStatusService.randomPaymentStatus());
    }
}
