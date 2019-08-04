package ua.alxmute.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.service.PaymentStatusService;

@RestController
@AllArgsConstructor
@RequestMapping("status")
public class StatusController {

    private PaymentStatusService paymentStatusService;

    @GetMapping
    public ResponseEntity<PaymentStatus> getRandomStatus() {
        return new ResponseEntity<>(paymentStatusService.randomPaymentStatus(), HttpStatus.OK);
    }
}
