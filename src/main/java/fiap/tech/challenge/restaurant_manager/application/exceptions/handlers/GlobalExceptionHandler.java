package fiap.tech.challenge.restaurant_manager.application.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.application.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.application.utils.ApiErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador global de exceções para erros técnicos genéricos
 * como validações, violação de constraints e erros de persistência.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ConstraintMessageResolver constraintMessageResolver;

    public GlobalExceptionHandler(ConstraintMessageResolver constraintMessageResolver) {
        this.constraintMessageResolver = constraintMessageResolver;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorArray> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        List<String> errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());

        return ApiErrorBuilder.build(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorArray> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ApiErrorBuilder.build(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorArray> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        String message = constraintMessageResolver.resolveMessage(ex.getMessage());
        return ApiErrorBuilder.build(HttpStatus.CONFLICT, message, request);
    }
}