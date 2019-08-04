package ua.alxmute.converter;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.dto.PaymentRequestDto;

@Component
@AllArgsConstructor
public class PaymentRequestToDtoConverter implements Converter<PaymentRequest, PaymentRequestDto> {

    private RouteToDtoConverter routeToDtoConverter;

    @Override
    public PaymentRequestDto convert(PaymentRequest source) {

        return PaymentRequestDto.builder()
                .id(source.getId())
                .route(routeToDtoConverter.convert(source.getRoute()))
                .departureTime(source.getDepartureTime())
                .paymentStatus(source.getPaymentStatus())
                .build();
    }
}
