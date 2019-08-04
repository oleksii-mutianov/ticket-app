package ua.alxmute.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.alxmute.data.access.domain.Route;
import ua.alxmute.data.access.repository.RouteRepository;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;
import ua.alxmute.exceptions.EntityNotFoundException;
import ua.alxmute.service.RouteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private ConversionService conversionService;
    private RouteRepository routeRepository;

    @Override
    public RouteDto findById(Long id) {

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        return conversionService.convert(route, RouteDto.class);
    }

    @Override
    public List<RouteDto> findAll() {
        return routeRepository.findAll()
                .stream().map(route -> conversionService.convert(route, RouteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RouteDto save(RouteCreateDto routeDto) {
        Route route = conversionService.convert(routeDto, Route.class);
        Route savedRoute = routeRepository.save(route);
        log.debug("{} was saved", route);
        return conversionService.convert(savedRoute, RouteDto.class);
    }
}
