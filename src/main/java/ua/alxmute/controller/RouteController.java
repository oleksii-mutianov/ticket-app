package ua.alxmute.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.dto.RouteCreateDto;
import ua.alxmute.dto.RouteDto;
import ua.alxmute.service.RouteService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("routes")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<RouteDto> save(@RequestBody @Valid RouteCreateDto route) {
        RouteDto savedRoute = routeService.save(route);
        return ResponseEntity.created(new URI(savedRoute.toString())).body(savedRoute);
    }

    @GetMapping
    public ResponseEntity<List<RouteDto>> findAll() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }
}
