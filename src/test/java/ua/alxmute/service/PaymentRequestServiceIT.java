package ua.alxmute.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.alxmute.config.AbstractIntegrationTest;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.dto.PaymentRequestIdDto;

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
        PaymentRequestDto paymentRequestDto = getPaymentRequestDto(paymentRequest);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestService.findById(paymentRequest.getId());

        // THEN
        assertEquals(paymentRequestDto, actualPaymentRequestDto);
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
        PaymentRequestCreateDto paymentRequestCreateDto = mockPaymentRequestCreateDto(paymentRequest);

        // WHEN
        PaymentRequestIdDto idDto = paymentRequestService.save(paymentRequestCreateDto);
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
        PaymentRequestDto paymentRequestDto = getPaymentRequestDto(paymentRequest);

        // WHEN
        List<PaymentRequestDto> paymentRequestDtos = paymentRequestService.findAll();

        // THEN
        assertThat(paymentRequestDtos).contains(paymentRequestDto);
    }

    @Test
    public void shouldUpdatePaymentStatus() {
        // GIVEN
        PaymentRequest paymentRequest = getPaymentRequest();
        PaymentRequestDto expectedPaymentRequestDto = getPaymentRequestDto(paymentRequest);
        expectedPaymentRequestDto.setPaymentStatus(PaymentStatus.SUCCESSFUL);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestService.updatePaymentStatus(paymentRequest.getId(), PaymentStatus.SUCCESSFUL);

        // THEN
        assertEquals(expectedPaymentRequestDto, actualPaymentRequestDto);
    }
}
