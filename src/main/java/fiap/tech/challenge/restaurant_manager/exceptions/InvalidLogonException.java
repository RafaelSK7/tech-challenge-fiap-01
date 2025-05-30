package fiap.tech.challenge.restaurant_manager.exceptions;

public class InvalidLogonException extends RuntimeException {
    
	public InvalidLogonException() {
        super("Login ou senha invalidos;");
    }
}
