package fiap.tech.challenge.restaurant_manager.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.controllers.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.*;
import fiap.tech.challenge.restaurant_manager.utils.ApiErrorBuilder;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global para exceções relacionadas ao domínio de Usuários.
 * 
 * Trata exceções como usuário não encontrado, e-mail inválido e endereço inválido,
 * retornando respostas padronizadas com os devidos códigos HTTP.
 */
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorArray> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler({
        InvalidEmailException.class,
        InvalidAddressException.class,
        InvalidUserTypeException.class
    })
    public ResponseEntity<ApiErrorArray> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<ApiErrorArray> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        return ApiErrorBuilder.build(status, ex.getMessage(), request);
    }
}
