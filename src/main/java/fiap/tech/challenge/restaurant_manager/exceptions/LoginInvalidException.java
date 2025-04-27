package fiap.tech.challenge.restaurant_manager.exceptions;

public class LoginInvalidException extends RuntimeException {
    public LoginInvalidException() {
        super("Login ou senha invalidos;");
    }
}
