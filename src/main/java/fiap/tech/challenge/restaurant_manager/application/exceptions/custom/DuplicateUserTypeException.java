package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class DuplicateUserTypeException extends RuntimeException {

    public DuplicateUserTypeException() {
        super("Nome do tipo de usuario ja existe");
    }
}
