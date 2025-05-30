package fiap.tech.challenge.restaurant_manager.services.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationService;

@Service
public class CreateUserUseCase {
	
	private final UserRepository userRepository;
	
	private List<ValidationService> createUserValidations;
	
	public CreateUserUseCase(UserRepository userRepository, List<ValidationService> createUserValidations) {
		this.userRepository = userRepository;
		this.createUserValidations = createUserValidations;
	}
	
	
	public UserResponse createUser(CreateUserRequest userRequest) {
		this.createUserValidations.forEach(v -> v.validate(userRequest));
		User newUser = new User(userRequest);
		return toResponse(userRepository.save(newUser));
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
