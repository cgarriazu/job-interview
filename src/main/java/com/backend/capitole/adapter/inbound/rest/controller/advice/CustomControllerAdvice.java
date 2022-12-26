package com.backend.capitole.adapter.inbound.rest.controller.advice;

import com.backend.capitole.adapter.inbound.rest.configuration.annotation.ErrorHandler;
import com.backend.capitole.adapter.inbound.rest.controller.dto.ErrorResponse;
import com.backend.capitole.core.domain.exception.PriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = ErrorHandler.class)
public class CustomControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException pnfe) {
        logger.error("PriceNotFoundException encountered: ", pnfe);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(pnfe.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<List<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException cve) {
        logger.error("ConstraintViolationException encountered: ", cve);
        return new ResponseEntity<>(cve.getConstraintViolations().stream()
                .map(violation -> ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(violation.getMessage())
                        .build())
                .collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException matme) {
        logger.error("MethodArgumentTypeMismatchException encountered: ", matme);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(matme.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("Exception encountered: ", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
