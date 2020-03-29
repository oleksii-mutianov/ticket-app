package ua.alxmute.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.data.access.repository.PaymentRequestRepository;
import ua.alxmute.data.access.repository.RouteRepository;
import ua.alxmute.dto.PaymentCreateDto;
import ua.alxmute.dto.PaymentResponseDto;
import ua.alxmute.dto.PaymentResponseIdDto;
import ua.alxmute.service.PaymentRequestService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private ConversionService conversionService;
    private PaymentRequestRepository paymentRequestRepository;
    private RouteRepository routeRepository;

    @Override
    public PaymentResponseDto findById(Long id) {

        PaymentRequest paymentRequest = paymentRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment Request not found"));

        return conversionService.convert(paymentRequest, PaymentResponseDto.class);
    }

    @Override
    public List<PaymentResponseDto> findAll() {
        return paymentRequestRepository.findAll()
                .stream().map(paymentRequest -> conversionService.convert(paymentRequest, PaymentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponseIdDto save(PaymentCreateDto paymentRequestDto) {

        Route route = routeRepository.findById(paymentRequestDto.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Route ID"));

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setRoute(route);
        paymentRequest.setDepartureTime(paymentRequestDto.getDepartureTime());

        PaymentRequest savedPaymentRequest = paymentRequestRepository.save(paymentRequest);
        log.debug("{} was saved", savedPaymentRequest);

        return new PaymentResponseIdDto(savedPaymentRequest.getId());
    }

    @Override
    @Transactional
    public PaymentResponseDto updatePaymentStatus(Long id, PaymentStatus paymentStatus) {

        PaymentRequest paymentRequest = paymentRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment Request not found"));

        paymentRequest.setPaymentStatus(paymentStatus);
        PaymentRequest savedPaymentRequest = paymentRequestRepository.save(paymentRequest);
        log.debug("{} changed status from {} to {}",
                paymentRequest, paymentRequest.getPaymentStatus(), savedPaymentRequest.getPaymentStatus());

        return conversionService.convert(savedPaymentRequest, PaymentResponseDto.class);
    }

    @Override
    public PaymentStatus getStatusById(Long id) {
        return findById(id).getPaymentStatus();
    }
}
