package fiap.tech.challenge.restaurant_manager.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.*;
import fiap.tech.challenge.restaurant_manager.utils.ApiErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global para exceções de autenticação.
 * 
 * Trata exceções específicas lançadas durante processos de login,
 * como credenciais inválidas ou falhas de autenticação.
 */
@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler({
        InvalidLoginException.class,
        InvalidLogonException.class,
        InvalidPasswordException.class
    })
    public ResponseEntity<ApiErrorArray> handleAuthenticationExceptions(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return buildBadRequest(ex, request);
    }

    private ResponseEntity<ApiErrorArray> buildBadRequest(Exception ex, HttpServletRequest request) {
        return ApiErrorBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
