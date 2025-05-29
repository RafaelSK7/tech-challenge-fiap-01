package fiap.tech.challenge.restaurant_manager.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.LoginInvalidException;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.usecase.CreateUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.DeleteUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.ReadUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.UpdateUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.validation.CreateUserValidationService;
import fiap.tech.challenge.restaurant_manager.services.validation.UpdateUserValidationService;

@Service
public class UserService {
	
	private CreateUserUseCase createUserUseCase;
	private UpdateUserUseCase updateUserUseCase;
	private ReadUserUseCase readUserUseCase;
	private DeleteUserUseCase deleteUserUseCase;
	
	public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, ReadUserUseCase readUserUseCase, DeleteUserUseCase deleteUserUseCase) {
		this.createUserUseCase = createUserUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.readUserUseCase = readUserUseCase;
		this.deleteUserUseCase = deleteUserUseCase;
	}

	public UserResponse createUser(CreateUserRequest userRequest) {		
		return createUserUseCase.createUser(userRequest);
		
	}

	public Page<UserResponse> findAll(Pageable page) {
		return readUserUseCase.findAll(page);
	}

	public UserResponse findById(Long id) {
		return readUserUseCase.findById(id);
	}

	public UserResponse updateUser(Long id, CreateUserRequest userRequest) {
		
		return updateUserUseCase.updateUser(id ,userRequest);
	}

	public void deleteUser(Long id) {
		deleteUserUseCase.deleteUser(id);
	}

}
