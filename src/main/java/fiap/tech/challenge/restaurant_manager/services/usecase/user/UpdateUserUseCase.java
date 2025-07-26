package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.services.UserTypeService;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.Address;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateUserService;

@Service
public class UpdateUserUseCase {

	private final UserRepository userRepository;

	private List<ValidateUserService> validateUserService;

	private UserTypeService userTypeService;

	public UpdateUserUseCase(UserRepository userRepository, List<ValidateUserService> validateUserService, UserTypeService userTypeService) {
		this.userRepository = userRepository;
		this.validateUserService = validateUserService;
		this.userTypeService = userTypeService;
	}

	public UserResponse updateUser(Long id, CreateUserRequest userRequest) {

		User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		this.validateUserService.forEach(v -> v.validate(userRequest));

		userToUpdate.setName(userRequest.name());
		userToUpdate.setEmail(userRequest.email());
		userToUpdate.setLogin(userRequest.login());
		userToUpdate.setPassword(userRequest.password());
		userToUpdate.setAddress(new Address(userRequest.address()));
		userToUpdate.setLastUpdate(LocalDateTime.now());

		Optional<UserType> optionalUserType = userTypeService.findByUserTypeId(userRequest.userTypeId());

		optionalUserType.ifPresentOrElse(
				userToUpdate::setUserType,
				() -> {
					throw new UserTypeNotFoundException(userRequest.userTypeId());
				}
		);

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
				user.getUserType().getUserTypeId(), addressResponse, user.getRestaurants());
	}
}
