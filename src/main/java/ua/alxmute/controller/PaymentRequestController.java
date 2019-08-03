package ua.alxmute.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.dto.PaymentRequestIdDto;
import ua.alxmute.service.PaymentRequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("payments")
public class PaymentRequestController {

    private PaymentRequestService paymentRequestService;

    @PostMapping
    public ResponseEntity<PaymentRequestIdDto> createPaymentRequest(@RequestBody @Valid PaymentRequestCreateDto paymentRequest) {
        return new ResponseEntity<>(paymentRequestService.save(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentRequestDto>> findAll() {
        return new ResponseEntity<>(paymentRequestService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentRequestDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(paymentRequestService.findById(id), HttpStatus.OK);
    }

    @GetMapping("{id}/status")
    public ResponseEntity<PaymentStatus> getStatus(@PathVariable("id") Long id) {
        return new ResponseEntity<>(paymentRequestService.getStatusById(id), HttpStatus.OK);
    }


}
