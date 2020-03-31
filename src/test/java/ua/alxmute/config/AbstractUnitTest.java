package ua.alxmute.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentCreateDto;
import ua.alxmute.dto.PaymentResponseDto;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitTest {

    public Route mockRoute() {
        return Route.builder()
                .id(1L)
                .arrivalCity("Dnipro")
                .departureCity("Kyiv")
                .build();
    }

    public RouteDto mockRouteDto() {
        Route route = mockRoute();
        return mockRouteDto(route);
    }

    public RouteDto mockRouteDto(Route route) {
        return RouteDto.builder()
                .id(route.getId())
                .arrivalCity(route.getArrivalCity())
                .departureCity(route.getDepartureCity())
                .build();
    }

    public Optional<Route> mockOptionalRoute() {
        return Optional.of(mockRoute());
    }

    public Optional<Route> mockOptionalRoute(Route route) {
        return Optional.of(route);
    }

    public RouteCreateDto mockRouteCreateDto() {
        Route route = mockRoute();
        return mockRouteCreateDto(route);
    }

    public RouteCreateDto mockRouteCreateDto(Route route) {
        return RouteCreateDto.builder()
                .arrivalCity(route.getArrivalCity())
                .departureCity(route.getDepartureCity())
                .build();
    }

    public PaymentRequest mockPaymentRequest() {
        Route route = mockRoute();
        return mockPaymentRequest(route);
    }

    public PaymentRequest mockPaymentRequest(Route route) {
        return PaymentRequest.builder()
                .id(1L)
                .route(route)
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .departureTime(LocalDateTime.of(2019, 11, 11, 11, 11))
                .build();
    }

    public Optional<PaymentRequest> mockOptionalPaymentRequest(PaymentRequest paymentRequest) {
        return Optional.of(paymentRequest);
    }

    public PaymentResponseDto mockPaymentRequestDto(RouteDto routeDto) {
        return PaymentResponseDto.builder()
                .id(1L)
                .route(routeDto)
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .departureTime(LocalDateTime.of(2019, 11, 11, 11, 11))
                .build();
    }

    public PaymentResponseDto mockPaymentRequestDto(PaymentRequest paymentRequest) {
        return PaymentResponseDto.builder()
                .id(paymentRequest.getId())
                .route(mockRouteDto())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .departureTime(paymentRequest.getDepartureTime())
                .build();
    }

    public PaymentResponseDto mockPaymentRequestDto() {
        RouteDto routeDto = mockRouteDto();
        return mockPaymentRequestDto(routeDto);
    }

    public PaymentCreateDto mockPaymentRequestCreateDto() {
        PaymentRequest paymentRequest = mockPaymentRequest();
        return mockPaymentRequestCreateDto(paymentRequest);
    }

    public PaymentCreateDto mockPaymentRequestCreateDto(PaymentRequest paymentRequest) {
        return new PaymentCreateDto(paymentRequest.getId(), paymentRequest.getDepartureTime());
    }
}
