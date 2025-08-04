package fiap.tech.challenge.restaurant_manager.application.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.application.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.utils.ApiErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestaurantExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiErrorArray> handleRestaurantNotFound(RestaurantNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<ApiErrorArray> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        return ApiErrorBuilder.build(status, ex.getMessage(), request);
    }
}
