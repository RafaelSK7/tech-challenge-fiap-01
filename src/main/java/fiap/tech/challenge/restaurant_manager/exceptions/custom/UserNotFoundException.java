package fiap.tech.challenge.restaurant_manager.exceptions.custom;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private Long userId;
    
	public UserNotFoundException(Long userId) {
        super("Usuário com o ID " + userId + " não encontrado.");
        this.userId = userId;
    }
    
    public UserNotFoundException(String userLogin) {
        super("Usuário com o login " + userLogin + " não encontrado.");
    }

}
