package ua.alxmute.converter;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteToDtoConverterTest extends AbstractUnitTest {

    @Spy
    private RouteToDtoConverter routeToDtoConverter;

    @Test
    public void shouldConvertRouteToDto() {
        // GIVEN
        Route route = mockRoute();
        RouteDto routeDto = mockRouteDto(route);

        // WHEN
        RouteDto actualRouteDto = routeToDtoConverter.convert(route);

        // THEN
        assertEquals(routeDto, actualRouteDto);
    }
}
