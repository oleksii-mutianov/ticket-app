package ua.alxmute.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.data.access.repository.PaymentRequestRepository;
import ua.alxmute.data.access.repository.RouteRepository;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.exceptions.EntityNotFoundException;
import ua.alxmute.service.impl.PaymentRequestServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentRequestServiceImplTest extends AbstractUnitTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private PaymentRequestServiceImpl paymentRequestService;

    @Test
    public void shouldFindPaymentStatusById() {
        // GIVEN
        PaymentRequest paymentRequest = mockPaymentRequest();
        PaymentRequestDto paymentRequestDto = mockPaymentRequestDto(paymentRequest);
        Optional<PaymentRequest> optionalPaymentRequest = mockOptionalPaymentRequest(paymentRequest);
        when(paymentRequestRepository.findById(1L)).thenReturn(optionalPaymentRequest);
        when(conversionService.convert(paymentRequest, PaymentRequestDto.class)).thenReturn(paymentRequestDto);

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
        when(conversionService.convert(paymentRequest, PaymentRequestDto.class)).thenReturn(paymentRequestDto);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestService.findById(paymentRequest.getId());

        // THEN
        assertEquals(paymentRequestDto, actualPaymentRequestDto);
        verify(paymentRequestRepository, times(1)).findById(paymentRequest.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenPaymentRequestNotFound() {
        // WHEN
        paymentRequestService.findById(2L);

        // THEN
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
        when(conversionService.convert(paymentRequest, PaymentRequestDto.class)).thenReturn(paymentRequestDto);
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
        when(conversionService.convert(paymentRequest, PaymentRequestDto.class)).thenReturn(paymentRequestDto);
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
