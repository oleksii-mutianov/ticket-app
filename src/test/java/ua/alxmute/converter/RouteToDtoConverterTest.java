package ua.alxmute.converter;

import org.junit.Test;
import org.mockito.InjectMocks;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteDto;

import static org.junit.Assert.assertEquals;

public class RouteToDtoConverterTest extends AbstractUnitTest {

    @InjectMocks
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
