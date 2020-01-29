package ua.alxmute.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.core.convert.support.GenericConversionService;
import ua.alxmute.config.AbstractUnitTest;
import ua.alxmute.converter.CreateDtoToRouteConverter;
import ua.alxmute.converter.RouteToDtoConverter;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.repository.RouteRepository;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;
import ua.alxmute.service.impl.RouteServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceImplTest extends AbstractUnitTest {

    @Spy
    private GenericConversionService conversionService;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @BeforeEach
    void setup() {
        conversionService.addConverter(new RouteToDtoConverter());
        conversionService.addConverter(new CreateDtoToRouteConverter());
    }

    @Test
    public void shouldFindRouteById() {
        // GIVEN
        Route route = mockRoute();
        Optional<Route> optionalRoute = mockOptionalRoute(route);
        RouteDto routeDto = mockRouteDto(route);
        when(routeRepository.findById(1L)).thenReturn(optionalRoute);

        // WHEN
        RouteDto actualRouteDto = routeService.findById(route.getId());

        // THEN
        assertEquals(routeDto, actualRouteDto);
        verify(routeRepository, times(1)).findById(route.getId());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenRouteNotFound() {
        // WHEN THEN
        assertThrows(EntityNotFoundException.class, () -> routeService.findById(2L));
        verify(routeRepository, times(1)).findById(2L);
    }

    @Test
    public void shouldFindAllRoutes() {
        // GIVEN
        Route route = mockRoute();
        RouteDto routeDto = mockRouteDto(route);
        List<Route> routes = Collections.singletonList(route);
        when(routeRepository.findAll()).thenReturn(routes);

        // WHEN
        List<RouteDto> routeDtos = routeService.findAll();

        // THEN
        assertThat(routeDtos).hasSize(routes.size());
        assertEquals(routeDto, routeDtos.get(0));
        verify(routeRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindNoRoutes() {
        // GIVEN
        when(routeRepository.findAll()).thenReturn(Collections.emptyList());

        // WHEN
        List<RouteDto> routeDtos = routeService.findAll();

        // THEN
        assertThat(routeDtos).isEmpty();
        verify(routeRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveRoute() {
        // GIVEN
        Route route = mockRoute();
        route.setId(null);
        RouteCreateDto routeCreateDto = mockRouteCreateDto(route);

        // WHEN
        routeService.save(routeCreateDto);

        // THEN
        verify(routeRepository, times(1)).save(route);
    }
}
