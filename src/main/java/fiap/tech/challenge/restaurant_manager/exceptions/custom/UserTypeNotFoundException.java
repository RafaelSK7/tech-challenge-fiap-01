package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException(Long id) {
        super("Tipo de Usuário com o ID " + id + " não encontrado.");
    }
}
