package ua.alxmute.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentCreateDto;
import ua.alxmute.dto.PaymentResponseDto;
import ua.alxmute.service.PaymentRequestService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
public class PaymentRequestController {

    private final PaymentRequestService paymentRequestService;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<?> createPaymentRequest(@RequestBody @Valid PaymentCreateDto paymentRequest) {
        var savedPaymentRequest = paymentRequestService.save(paymentRequest);
        URI location = new URI(savedPaymentRequest.getPaymentRequestId().toString());
        return ResponseEntity.created(location).body(savedPaymentRequest);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(paymentRequestService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentResponseDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentRequestService.findById(id));
    }

    @GetMapping("{id}/status")
    public ResponseEntity<PaymentStatus> getStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentRequestService.getStatusById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentResponseDto> updateStatus(@PathVariable("id") Long id, @RequestBody PaymentStatus paymentStatus) {
        return ResponseEntity.ok(paymentRequestService.updatePaymentStatus(id, paymentStatus));
    }

}
