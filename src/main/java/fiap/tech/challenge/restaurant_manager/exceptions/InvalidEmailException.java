package fiap.tech.challenge.restaurant_manager.exceptions;

public class InvalidEmailException extends RuntimeException{
	
    public InvalidEmailException() {
        super("Email inválido");
    }
}
