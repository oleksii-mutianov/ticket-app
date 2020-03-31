package ua.alxmute.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.alxmute.config.AbstractIntegrationTest;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentCreateDto;
import ua.alxmute.dto.PaymentResponseDto;
import ua.alxmute.dto.PaymentResponseIdDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentRequestServiceIT extends AbstractIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Test
    public void shouldFindPaymentStatusById() {
        // GIVEN
        PaymentRequest paymentRequest = getPaymentRequest();

        // WHEN
        PaymentStatus actualStatus = paymentRequestService.getStatusById(paymentRequest.getId());

        // THEN
        assertEquals(paymentRequest.getPaymentStatus(), actualStatus);
    }

    @Test
    public void shouldFindPaymentRequestById() {
        // GIVEN
        PaymentRequest paymentRequest = getPaymentRequest();
        PaymentResponseDto paymentResponseDto = getPaymentRequestDto(paymentRequest);

        // WHEN
        PaymentResponseDto actualPaymentResponseDto = paymentRequestService.findById(paymentRequest.getId());

        // THEN
        assertEquals(paymentResponseDto, actualPaymentResponseDto);
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenPaymentRequestNotFound() {
        // WHEN THEN
        assertThrows(EntityNotFoundException.class, () -> paymentRequestService.findById(100000L));
    }

    @Test
    public void shouldSavePaymentRequest() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        PaymentCreateDto paymentCreateDto = mockPaymentRequestCreateDto(paymentRequest);

        // WHEN
        PaymentResponseIdDto idDto = paymentRequestService.save(paymentCreateDto);
        PaymentRequest actualPaymentRequest = entityManager.find(PaymentRequest.class, idDto.getPaymentRequestId());

        // THEN
        assertEquals(paymentRequest.getPaymentStatus(), actualPaymentRequest.getPaymentStatus());
        assertEquals(paymentRequest.getDepartureTime(), actualPaymentRequest.getDepartureTime());
        assertEquals(paymentRequest.getRoute(), actualPaymentRequest.getRoute());
    }

    @Test
    public void shouldContainCreatedPaymentRequestWhenFindAll() {
        // GIVEN
        PaymentRequest paymentRequest = getPaymentRequest();
        PaymentResponseDto paymentResponseDto = getPaymentRequestDto(paymentRequest);

        // WHEN
        List<PaymentResponseDto> paymentResponseDtos = paymentRequestService.findAll();

        // THEN
        assertThat(paymentResponseDtos).contains(paymentResponseDto);
    }

    @Test
    public void shouldUpdatePaymentStatus() {
        // GIVEN
        PaymentRequest paymentRequest = getPaymentRequest();
        PaymentResponseDto expectedPaymentResponseDto = getPaymentRequestDto(paymentRequest);
        expectedPaymentResponseDto.setPaymentStatus(PaymentStatus.SUCCESSFUL);

        // WHEN
        PaymentResponseDto actualPaymentResponseDto = paymentRequestService.updatePaymentStatus(paymentRequest.getId(), PaymentStatus.SUCCESSFUL);

        // THEN
        assertEquals(expectedPaymentResponseDto, actualPaymentResponseDto);
    }
}
