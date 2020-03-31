package ua.alxmute.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.dto.PaymentResponseDto;

@Component
@RequiredArgsConstructor
public class PaymentRequestToDtoConverter implements Converter<PaymentRequest, PaymentResponseDto> {

    private final RouteToDtoConverter routeToDtoConverter;

    @Override
    public PaymentResponseDto convert(PaymentRequest source) {
        return PaymentResponseDto.builder()
                .id(source.getId())
                .route(routeToDtoConverter.convert(source.getRoute()))
                .departureTime(source.getDepartureTime())
                .paymentStatus(source.getPaymentStatus())
                .build();
    }
}
