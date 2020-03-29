package ua.alxmute.converter;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.dto.PaymentResponseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PaymentRequestToDtoConverterTest extends AbstractUnitTest {

    @Mock
    private RouteToDtoConverter routeToDtoConverter;

    @InjectMocks
    private PaymentRequestToDtoConverter paymentRequestToDtoConverter;

    @Test
    public void shouldConvertPaymentRequestToDto() {
        // GIVEN
        when(routeToDtoConverter.convert(any())).thenReturn(mockRouteDto());
        PaymentRequest paymentRequest = mockPaymentRequest();
        PaymentResponseDto dto = mockPaymentRequestDto(paymentRequest);

        // WHEN
        PaymentResponseDto actualPaymentResponseDto = paymentRequestToDtoConverter.convert(paymentRequest);

        // THEN
        assertEquals(dto, actualPaymentResponseDto);
    }
}
