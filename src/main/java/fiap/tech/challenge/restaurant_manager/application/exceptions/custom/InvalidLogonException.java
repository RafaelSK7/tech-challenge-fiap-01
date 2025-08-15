package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class InvalidLogonException extends RuntimeException {
    
	public InvalidLogonException() {
        super("Login ou senha invalidos;");
    }
}
