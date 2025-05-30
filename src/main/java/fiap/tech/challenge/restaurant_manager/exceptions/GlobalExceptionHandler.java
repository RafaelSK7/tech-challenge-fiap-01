package fiap.tech.challenge.restaurant_manager.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fiap.tech.challenge.restaurant_manager.controllers.exceptions.ApiErrorArray;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

/**
 * Classe responsável pelo tratamento global de exceções na aplicação.
 * Captura e trata exceções específicas lançadas pelos controllers,
 * retornando respostas HTTP apropriadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções do tipo {@link UserNotFoundException} e retorna HTTP 404 (Not
     * Found).
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Trata exceções do tipo {@link InvalidLogonException} e retorna HTTP 400 (Bad
     * Request).
     */
    @ExceptionHandler(InvalidLogonException.class)
    public ResponseEntity<String> handleInvalidLogin(InvalidLogonException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceções do tipo {@link InvalidEmailException} e retorna HTTP 400 (Bad
     * Request).
     */
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmail(InvalidEmailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Trata exceções do tipo {@link InvalidAddressException} e retorna HTTP 400 (Bad
     * Request).
     */
    @ExceptionHandler(InvalidAddressException.class)
    public ResponseEntity<String> handleInvalidAddress(InvalidAddressException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Trata exceções do tipo {@link InvalidAddressException} e retorna HTTP 400 (Bad
     * Request).
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidAddress(InvalidPasswordException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Trata exceções do tipo {@link InvalidLoginException} e retorna HTTP 400 (Bad
     * Request).
     */
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLogin(InvalidLoginException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
   
    /**
     * Trata exceções de integridade de dados, como violações de chave única,
     * retornando HTTP 409 (Conflict).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorArray> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String fieldName = getFieldNameFromException(ex);
        String errorMessage = fieldName + " já está cadastrado";

        ApiErrorArray apiError = new ApiErrorArray(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                List.of(errorMessage),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    /**
     * Trata exceções de validação de argumentos do controller (Bean Validation),
     * retornando HTTP 400 com a lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorArray> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorArray apiError = new ApiErrorArray(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Trata exceções de validação de constraints em nível de bean ou path,
     * retornando HTTP 400 com a lista de erros.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorArray> handleConstraintViolationException(ConstraintViolationException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ApiErrorArray apiError = new ApiErrorArray(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Extrai o nome do campo envolvido em uma exceção de integridade de dados,
     * analisando a mensagem da exceção e retornando o nome do campo
     * identificado ou "Campo" caso não encontrado.
     */
    private String getFieldNameFromException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        if (message != null) {
            if (message.contains("Unique index or primary key violation")) {
                String[] parts = message.split("ON");
                if (parts.length > 1) {
                    String constraint = parts[1].trim();
                    if (constraint.contains("LOGIN")) {
                        return "Login";
                    }
                    if (constraint.contains("EMAIL")) {
                        return "Email";
                    }
                }
            }
        }
        return "Campo";
    }

}
