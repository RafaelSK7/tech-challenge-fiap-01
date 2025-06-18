package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidLogonException extends RuntimeException {
    
	public InvalidLogonException() {
        super("Login ou senha invalidos;");
    }
}
