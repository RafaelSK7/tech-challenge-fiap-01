package fiap.tech.challenge.restaurant_manager.exceptions;

public class InvalidPasswordException extends RuntimeException {
	
	public InvalidPasswordException() {
		super("O password não pode ser nulo ou vazio!");
	}
}
