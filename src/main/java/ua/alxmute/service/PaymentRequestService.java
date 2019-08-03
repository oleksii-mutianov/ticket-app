package ua.alxmute.service;

import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.dto.PaymentRequestIdDto;

import java.util.List;

public interface PaymentRequestService {

    PaymentRequestDto findById(Long id);

    List<PaymentRequestDto> findAll();

    PaymentRequestIdDto save(PaymentRequestCreateDto paymentRequest);

    PaymentStatus getStatusById(Long id);

}
