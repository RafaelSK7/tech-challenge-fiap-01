package fiap.tech.challenge.restaurant_manager.services.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.UpdateUserValidationService;

@Service
public class UpdateUserUseCase {

	private final UserRepository userRepository;

	private List<UpdateUserValidationService> updateUserValidations;

	public UpdateUserUseCase(UserRepository userRepository, List<UpdateUserValidationService> updateUserValidations) {
		this.userRepository = userRepository;
		this.updateUserValidations = updateUserValidations;
	}

	public UserResponse updateUser(Long id, CreateUserRequest userRequest) {

		User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		this.updateUserValidations.forEach(v -> v.validate(userRequest, userToUpdate));

		userToUpdate.setName(userRequest.name());
		userToUpdate.setEmail(userRequest.email());
		userToUpdate.setLogin(userRequest.login());
		userToUpdate.setPassword(userRequest.password());
		// criar uma controller com uma service para atualizar o endereço
		// updateAddressByUserId
		userToUpdate.setUserType(userRequest.userType());
		userToUpdate.setLastUpdate(LocalDateTime.now());

		return toResponse(userRepository.save(userToUpdate));
	}

	private UserResponse toResponse(User user) {
		AddressResponse addressResponse = null;

		if (user.getAddress() != null) {
			addressResponse = new AddressResponse(user.getAddress().getStreet(), user.getAddress().getNumber(),
					user.getAddress().getNeighborhood(), user.getAddress().getCity(), user.getAddress().getState(),
					user.getAddress().getZipCode(), user.getAddress().getCountry());
		}

		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getLogin(),
				user.getUserType().name(), addressResponse);
	}
}
