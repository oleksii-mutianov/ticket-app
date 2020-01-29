package ua.alxmute.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.core.convert.support.GenericConversionService;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.converter.PaymentRequestToDtoConverter;
import ua.alxmute.converter.RouteToDtoConverter;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.data.access.repository.PaymentRequestRepository;
import ua.alxmute.data.access.repository.RouteRepository;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.service.impl.PaymentRequestServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentRequestServiceImplTest extends AbstractUnitTest {

    @Spy
    private GenericConversionService conversionService;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private PaymentRequestServiceImpl paymentRequestService;

    @BeforeEach
    void setup() {
        conversionService.addConverter(new PaymentRequestToDtoConverter(new RouteToDtoConverter()));
    }

    @Test
    public void shouldFindPaymentStatusById() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        Optional<PaymentRequest> optionalPaymentRequest = mockOptionalPaymentRequest(paymentRequest);
        when(paymentRequestRepository.findById(1L)).thenReturn(optionalPaymentRequest);

        // WHEN
        PaymentStatus actualStatus = paymentRequestService.getStatusById(paymentRequest.getId());

        // THEN
        assertEquals(paymentRequest.getPaymentStatus(), actualStatus);
        verify(paymentRequestRepository, times(1)).findById(paymentRequest.getId());
    }

    @Test
    public void shouldFindPaymentRequestById() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        PaymentRequestDto paymentRequestDto = mockPaymentRequestDto(paymentRequest);
        Optional<PaymentRequest> optionalPaymentRequest = mockOptionalPaymentRequest(paymentRequest);
        when(paymentRequestRepository.findById(1L)).thenReturn(optionalPaymentRequest);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestService.findById(paymentRequest.getId());

        // THEN
        assertEquals(paymentRequestDto, actualPaymentRequestDto);
        verify(paymentRequestRepository, times(1)).findById(paymentRequest.getId());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenPaymentRequestNotFound() {
        // WHEN THEN
        assertThrows(EntityNotFoundException.class, () -> paymentRequestService.findById(2L));
        verify(paymentRequestRepository, times(1)).findById(2L);
    }

    @Test
    public void shouldSavePaymentRequest() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        Optional<Route> optionalRoute = mockOptionalRoute(paymentRequest.getRoute());
        PaymentRequestCreateDto paymentRequestCreateDto = mockPaymentRequestCreateDto(paymentRequest);
        when(routeRepository.findById(1L)).thenReturn(optionalRoute);
        when(paymentRequestRepository.save(any())).thenReturn(paymentRequest);

        // WHEN
        paymentRequestService.save(paymentRequestCreateDto);

        // THEN
        verify(paymentRequestRepository, times(1)).save(any());
    }

    @Test
    public void shouldFindAllPaymentRequests() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        PaymentRequestDto paymentRequestDto = mockPaymentRequestDto(paymentRequest);
        List<PaymentRequest> paymentRequests = Collections.singletonList(paymentRequest);
        when(paymentRequestRepository.findAll()).thenReturn(paymentRequests);

        // WHEN
        List<PaymentRequestDto> paymentRequestDtos = paymentRequestService.findAll();

        // THEN
        assertThat(paymentRequestDtos).hasSize(paymentRequests.size());
        assertEquals(paymentRequestDto, paymentRequestDtos.get(0));
        verify(paymentRequestRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindNoPaymentRequests() {
        // GIVEN
        when(paymentRequestRepository.findAll()).thenReturn(Collections.emptyList());

        // WHEN
        List<PaymentRequestDto> paymentRequestDtos = paymentRequestService.findAll();

        // THEN
        assertThat(paymentRequestDtos).isEmpty();
        verify(paymentRequestRepository, times(1)).findAll();
    }

    @Test
    public void shouldUpdatePaymentStatus() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        paymentRequest.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        PaymentRequestDto paymentRequestDto = mockPaymentRequestDto(paymentRequest);
        Optional<PaymentRequest> optionalPaymentRequest = mockOptionalPaymentRequest(paymentRequest);
        when(paymentRequestRepository.findById(1L)).thenReturn(optionalPaymentRequest);
        when(paymentRequestRepository.save(any())).thenReturn(paymentRequest);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestService.updatePaymentStatus(paymentRequest.getId(), PaymentStatus.SUCCESSFUL);

        // THEN
        assertEquals(paymentRequestDto, actualPaymentRequestDto);
        verify(paymentRequestRepository, times(1)).findById(paymentRequest.getId());
        verify(paymentRequestRepository, times(1)).save(paymentRequest);
    }
}
