package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UpdateUserUseCase {

	private final UsersGateway usersGateway;

	private List<ValidateUserService> validateUserService;

	private UserTypeController userTypeController;

	public UpdateUserUseCase(UsersGateway usersGateway, List<ValidateUserService> validateUserService, UserTypeController userTypeController) {
		this.usersGateway = usersGateway;
		this.validateUserService = validateUserService;
		this.userTypeController = userTypeController;
	}

	public UserResponse updateUser(Long id, CreateUserRequest userRequest) {
		log.info("Entrou no use case de atualizacao do usuario.");
		log.info("Buscando usuario a ser atualizado.");
		UsersEntity userToUpdate = usersGateway.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));

		this.validateUserService.forEach(v -> v.validate(userRequest));
		log.info("Populando os campos do usuario.");
		userToUpdate.setName(userRequest.name());
		userToUpdate.setEmail(userRequest.email());
		userToUpdate.setLogin(userRequest.login());
		userToUpdate.setPassword(userRequest.password());
		userToUpdate.setAddress(new AddressEntity(userRequest.address()));
		userToUpdate.setLastUpdate(LocalDateTime.now());
		log.info("Buscando restaurante ao qual pertence o usuario.");
		userToUpdate.setUserType(userTypeController.findByIdEntity(userRequest.userTypeId()));
		log.info("Usuario populado.");
		return toResponse(usersGateway.update(userToUpdate));
	}

	private UserResponse toResponse(UsersEntity user) {
		log.info("Montando DTO de retorno do usuario.");
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
