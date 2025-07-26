package fiap.tech.challenge.restaurant_manager.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.exceptions.custom.CardapioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fiap.tech.challenge.restaurant_manager.controllers.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.utils.ApiErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class MenuItemExceptionHandler {

        @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorArray> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    // Para cada nova classe de exceção personalizada, adicione ela aqui - Jonathan
    @ExceptionHandler({
            CardapioException.class
    })
    public ResponseEntity<ApiErrorArray> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }
    @ExceptionHandler({
            InvalidMenuItemException.class
    })
    public ResponseEntity<ApiErrorArray> handleNotFoundExceptions(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<ApiErrorArray> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        return ApiErrorBuilder.build(status, ex.getMessage(), request);
    }

}
