package ua.alxmute.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.dto.PaymentRequestCreateDto;
import ua.alxmute.dto.PaymentRequestDto;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class AbstractIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    public Route mockRoute() {
        return Route.builder()
                .arrivalCity("Dnipro")
                .departureCity("Kyiv")
                .build();
    }

    public Route getRoute() {
        Route route = Route.builder()
                .arrivalCity("Dnipro")
                .departureCity("Kyiv")
                .build();
        entityManager.persist(route);
        return route;
    }

    public RouteDto getRouteDto(Route route) {
        return RouteDto.builder()
                .id(route.getId())
                .arrivalCity(route.getArrivalCity())
                .departureCity(route.getDepartureCity())
                .build();
    }

    public RouteCreateDto getRouteCreateDto(Route route) {
        return RouteCreateDto.builder()
                .arrivalCity(route.getArrivalCity())
                .departureCity(route.getDepartureCity())
                .build();
    }

    public PaymentRequest getPaymentRequest() {
        Route route = mockRoute();
        return getPaymentRequest(route);
    }

    public PaymentRequest getPaymentRequest(Route route) {
        entityManager.persist(route);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .route(route)
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .departureTime(LocalDateTime.of(2019, 11, 11, 11, 11))
                .build();
        entityManager.persist(paymentRequest);
        return paymentRequest;
    }

    public PaymentRequest mockPaymentRequest() {
        return PaymentRequest.builder()
                .route(getRoute())
                .paymentStatus(PaymentStatus.IN_PROGRESS)
                .departureTime(LocalDateTime.of(2019, 11, 11, 11, 11))
                .build();
    }

    public PaymentRequestDto getPaymentRequestDto(PaymentRequest paymentRequest) {
        return PaymentRequestDto.builder()
                .id(paymentRequest.getId())
                .route(getRouteDto(paymentRequest.getRoute()))
                .paymentStatus(paymentRequest.getPaymentStatus())
                .departureTime(paymentRequest.getDepartureTime())
                .build();
    }

    public PaymentRequestCreateDto mockPaymentRequestCreateDto(PaymentRequest paymentRequest) {
        return new PaymentRequestCreateDto(paymentRequest.getRoute().getId(), paymentRequest.getDepartureTime());
    }
}
