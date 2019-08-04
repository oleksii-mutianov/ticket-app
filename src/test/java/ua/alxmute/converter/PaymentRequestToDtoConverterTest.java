package ua.alxmute.converter;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.dto.PaymentRequestDto;

import static org.junit.Assert.assertEquals;
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
        PaymentRequestDto dto = mockPaymentRequestDto(paymentRequest);

        // WHEN
        PaymentRequestDto actualPaymentRequestDto = paymentRequestToDtoConverter.convert(paymentRequest);

        // THEN
        assertEquals(dto, actualPaymentRequestDto);
    }
}
