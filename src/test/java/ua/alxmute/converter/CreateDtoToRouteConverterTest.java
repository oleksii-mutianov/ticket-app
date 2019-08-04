package ua.alxmute.converter;

import org.junit.Test;
import org.mockito.InjectMocks;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteCreateDto;

import static org.junit.Assert.assertEquals;

public class CreateDtoToRouteConverterTest extends AbstractUnitTest {

    @InjectMocks
    private CreateDtoToRouteConverter createDtoToRouteConverter;

    @Test
    public void shouldConvertCreateDtoToRoute() {

        // GIVEN
        Route route = mockRoute();
        route.setId(null);
        RouteCreateDto routeCreateDto = mockRouteCreateDto(route);

        // WHEN
        Route actualRoute = createDtoToRouteConverter.convert(routeCreateDto);

        // THEN
        assertEquals(route, actualRoute);
    }
}
