package com.gustalencar.horus.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.*;
import org.hibernate.TransientObjectException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.loader.NonUniqueDiscoveredSqlAliasException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandardError> handleResourceNotFoundException(final ResourceNotFoundException ex, final HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(StandardError.builder()
                .timestamp(now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandardError> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(StandardError.builder()
                .timestamp(now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<StandardError> handleIllegalArgumentException(final IllegalArgumentException ex, final HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(StandardError.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<StandardError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        var error = ValidationException.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error("Validation Exception")
                .message("Exception in validation attributes")
                .path(request.getRequestURI())
                .erros(new ArrayList<>())
                .build();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AmountOfPointsTheDayReached.class)
    ResponseEntity<StandardError> handleAmountOfPointsTheDayReached(final AmountOfPointsTheDayReached ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(StandardError.builder()
                .timestamp(now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<StandardError> handleAmountOfPointsTheDayReached(final NoSuchElementException ex, final HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(StandardError.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(NonUniqueDiscoveredSqlAliasException.class)
    ResponseEntity<StandardError> handleNonUniqueDiscoveredSqlAliasException(final NonUniqueDiscoveredSqlAliasException ex, final HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(StandardError.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    ResponseEntity<StandardError> handleInvalidDataAccessResourceUsageException(final InvalidDataAccessResourceUsageException ex, final HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(StandardError.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(NotUpdateHoursUserException.class)
    ResponseEntity<StandardError> handleNotUpdateHoursUserException(final NotUpdateHoursUserException ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(StandardError.builder()
                .timestamp(now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(TransientObjectException.class)
    ResponseEntity<StandardError> handleTransientObjectException(final TransientObjectException ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(StandardError.builder()
                .timestamp(now())
                .status(CONFLICT.value())
                .error(CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
    }

}
