package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class InvalidLoginException extends RuntimeException{
	
	public InvalidLoginException() {
		super("O login deve ser preenchido");
	}

}
