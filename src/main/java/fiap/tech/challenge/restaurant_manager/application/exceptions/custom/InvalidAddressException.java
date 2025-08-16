package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class InvalidAddressException extends RuntimeException{
	
	public InvalidAddressException(String message) {
		super(message);
	}

}
