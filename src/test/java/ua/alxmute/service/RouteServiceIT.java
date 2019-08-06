package ua.alxmute.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.alxmute.config.AbstractIntegrationTest;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class RouteServiceIT extends AbstractIntegrationTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldFindRouteById() {
        // GIVEN
        Route route = getRoute();
        RouteDto expectedRouteDto = getRouteDto(route);

        // WHEN
        RouteDto actualRouteDto = routeService.findById(route.getId());

        // THEN
        assertEquals(expectedRouteDto, actualRouteDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenRouteNotFound() {
        // WHEN
        routeService.findById(100000L);

        // THEN throw EntityNotFoundException
    }

    @Test
    public void shouldContainCreatedRouteWhenFindAll() {
        // GIVEN
        Route route = getRoute();
        RouteDto routeDto = getRouteDto(route);

        // WHEN
        List<RouteDto> actualRouteDtos = routeService.findAll();

        // THEN
        assertThat(actualRouteDtos).contains(routeDto);
    }

    @Test
    public void shouldSaveRoute() {
        // GIVEN
        Route route = mockRoute();
        RouteCreateDto routeCreateDto = getRouteCreateDto(route);

        // WHEN
        RouteDto actualRouteDto = routeService.save(routeCreateDto);

        // THEN
        assertEquals(route.getDepartureCity(), actualRouteDto.getDepartureCity());
        assertEquals(route.getArrivalCity(), actualRouteDto.getArrivalCity());
    }

}
