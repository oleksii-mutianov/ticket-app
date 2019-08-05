package ua.alxmute.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ua.alxmute.exceptions.EntityNotFoundException;
import ua.alxmute.exceptions.ErrorDetails;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> entityNotFoundHandler(EntityNotFoundException ex) {
        log.warn("An exception occurred!", ex);
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), notFound.value(), notFound.name()), notFound);
    }

}
