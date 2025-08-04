package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserRepository;

@Service
@Slf4j
public class DeleteUserUseCase {

	private UserRepository userRepository;

	public DeleteUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void deleteUser(Long id) {
		log.info("Entrou no use case de remocao do usuario");
		log.info("Buscando usuario para remocao.");
		UsersEntity userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		log.info("Usuario encontrado.");
		userRepository.delete(userToDelete);
		log.info("Usuario removido com sucesso.");
	}

}
