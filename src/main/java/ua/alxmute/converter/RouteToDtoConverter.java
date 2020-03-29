package ua.alxmute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.dto.RouteDto;

@Component
public class RouteToDtoConverter implements Converter<Route, RouteDto> {
    @Override
    public RouteDto convert(Route source) {
        return RouteDto.builder()
                .id(source.getId())
                .arrivalCity(source.getArrivalCity())
                .departureCity(source.getDepartureCity())
                .build();
    }
}
