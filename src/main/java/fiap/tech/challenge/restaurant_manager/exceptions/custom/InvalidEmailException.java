package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidEmailException extends RuntimeException{
	
    public InvalidEmailException() {
        super("Email inválido");
    }
}
