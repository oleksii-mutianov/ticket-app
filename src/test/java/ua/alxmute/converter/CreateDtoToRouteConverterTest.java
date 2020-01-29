package ua.alxmute.converter;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteCreateDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateDtoToRouteConverterTest extends AbstractUnitTest {

    @Spy
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
