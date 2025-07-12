package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class DuplicateUserTypeException extends RuntimeException {

    public DuplicateUserTypeException() {
        super("Nome do user type já existe");
    }
}
