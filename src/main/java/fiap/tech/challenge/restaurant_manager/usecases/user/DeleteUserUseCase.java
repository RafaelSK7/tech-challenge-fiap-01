package fiap.tech.challenge.restaurant_manager.usecases.user;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;

@Service
public class DeleteUserUseCase {

	private UserRepository userRepository;

	public DeleteUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void deleteUser(Long id) {
		User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userRepository.delete(userToDelete);
	}

}
