package io.nikolamicic21.simpletodoapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        final var message = exception.getFieldError().getDefaultMessage();
        log.error("Error occurred: {}", message);

        return ResponseEntity
                .badRequest()
                .body(message);
    }
}
