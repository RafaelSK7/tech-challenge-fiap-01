package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidUserTypeException extends RuntimeException {

    public InvalidUserTypeException() {
        super("Nome do user type inválido");
    }
}
