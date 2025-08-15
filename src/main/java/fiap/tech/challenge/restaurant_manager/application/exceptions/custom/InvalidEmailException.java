package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class InvalidEmailException extends RuntimeException{
	
    public InvalidEmailException() {
        super("Email inválido");
    }
}
