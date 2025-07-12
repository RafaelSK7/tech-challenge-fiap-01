package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class UserNotFoundException extends RuntimeException {
    
	public UserNotFoundException(Long userId) {
        super("Usuário com o ID " + userId + " não encontrado.");
    }
    
    public UserNotFoundException(String userLogin) {
        super("Usuário com o login " + userLogin + " não encontrado.");
    }
}
