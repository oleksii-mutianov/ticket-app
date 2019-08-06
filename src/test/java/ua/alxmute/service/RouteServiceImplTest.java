package ua.alxmute.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import ua.alxmute.config.AbstractUnitTest;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceImplTest extends AbstractUnitTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @Test
    public void shouldFindRouteById() {
        // GIVEN
        Route route = mockRoute();
        Optional<Route> optionalRoute = mockOptionalRoute(route);
        RouteDto routeDto = mockRouteDto(route);
        when(routeRepository.findById(1L)).thenReturn(optionalRoute);
        when(conversionService.convert(route, RouteDto.class)).thenReturn(routeDto);

        // WHEN
        RouteDto actualRouteDto = routeService.findById(route.getId());

        // THEN
        assertEquals(routeDto, actualRouteDto);
        verify(routeRepository, times(1)).findById(route.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenRouteNotFound() {
        // WHEN
        routeService.findById(2L);

        // THEN
        verify(routeRepository, times(1)).findById(2L);
    }

    @Test
    public void shouldFindAllRoutes() {
        // GIVEN
        Route route = mockRoute();
        RouteDto routeDto = mockRouteDto(route);
        List<Route> routes = Collections.singletonList(route);
        when(conversionService.convert(route, RouteDto.class)).thenReturn(routeDto);
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
        when(conversionService.convert(routeCreateDto, Route.class)).thenReturn(route);

        // WHEN
        routeService.save(routeCreateDto);

        // THEN
        verify(routeRepository, times(1)).save(route);
    }
}
