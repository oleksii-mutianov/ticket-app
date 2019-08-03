package ua.alxmute.service;

import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;

import java.util.List;

public interface RouteService {

    RouteDto findById(Long id);

    List<RouteDto> findAll();

    RouteDto save(RouteCreateDto route);
}
