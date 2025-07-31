package fiap.tech.challenge.restaurant_manager.exceptions.handlers;

import fiap.tech.challenge.restaurant_manager.exceptions.ApiErrorArray;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidUserTypeException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.utils.ApiErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Handler global para exceções relacionadas ao domínio de Tipos de Usuários.
 * <p>
 * Trata exceções como tipo de usuário duplicado ou tipo de usuário inválido,
 * retornando respostas padronizadas com os devidos códigos HTTP.
 */
@RestControllerAdvice
public class UserTypeExceptionHandler {

    @ExceptionHandler(UserTypeNotFoundException.class)
    public ResponseEntity<ApiErrorArray> handleUserTypeNotFound(UserTypeNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler({
            InvalidUserTypeException.class,
            DuplicateUserTypeException.class
    })
    public ResponseEntity<ApiErrorArray> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<ApiErrorArray> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        return ApiErrorBuilder.build(status, ex.getMessage(), request);
    }

}
