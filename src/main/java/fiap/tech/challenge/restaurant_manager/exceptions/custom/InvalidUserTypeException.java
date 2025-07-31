package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidUserTypeException extends RuntimeException {

    public InvalidUserTypeException(String message) {
        super(message);
    }
}
