package ua.alxmute.dto;

import lombok.Builder;
import lombok.Data;
import ua.alxmute.data.access.domain.enums.PaymentStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponseDto {

    private Long id;
    private RouteDto route;
    private LocalDateTime departureTime;
    private PaymentStatus paymentStatus;

}
