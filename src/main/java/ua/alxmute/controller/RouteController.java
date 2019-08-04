package ua.alxmute.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("routes")
public class RouteController {

    private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteDto> save(@RequestBody @Valid RouteCreateDto route) {
        return new ResponseEntity<>(routeService.save(route), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteDto>> findAll() {
        return new ResponseEntity<>(routeService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RouteDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(routeService.findById(id), HttpStatus.OK);
    }
}
