package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class InvalidPasswordException extends RuntimeException {
	
	public InvalidPasswordException() {
		super("O password não pode ser nulo ou vazio!");
	}
}
