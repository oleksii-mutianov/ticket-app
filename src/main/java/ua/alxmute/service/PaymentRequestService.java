package ua.alxmute.service;

import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentCreateDto;
import ua.alxmute.dto.PaymentResponseDto;
import ua.alxmute.dto.PaymentResponseIdDto;

import java.util.List;

public interface PaymentRequestService {

    PaymentResponseDto findById(Long id);

    List<PaymentResponseDto> findAll();

    PaymentResponseIdDto save(PaymentCreateDto paymentRequest);

    PaymentResponseDto updatePaymentStatus(Long id, PaymentStatus paymentStatus);

    PaymentStatus getStatusById(Long id);

}
