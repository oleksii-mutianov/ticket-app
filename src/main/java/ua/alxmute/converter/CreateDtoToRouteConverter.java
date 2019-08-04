package ua.alxmute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteCreateDto;

@Component
public class CreateDtoToRouteConverter implements Converter<RouteCreateDto, Route> {
    @Override
    public Route convert(RouteCreateDto source) {
        return Route.builder()
                .arrivalCity(source.getArrivalCity())
                .departureCity(source.getDepartureCity())
                .build();
    }
}
