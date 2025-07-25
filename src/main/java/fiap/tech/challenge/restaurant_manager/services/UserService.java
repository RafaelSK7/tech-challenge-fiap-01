package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.services.usecase.user.CreateUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.user.DeleteUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.user.ReadUserUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.user.UpdateUserUseCase;

@Service
public class UserService {

	private CreateUserUseCase createUserUseCase;
	private UpdateUserUseCase updateUserUseCase;
	private ReadUserUseCase readUserUseCase;
	private DeleteUserUseCase deleteUserUseCase;

	public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
			ReadUserUseCase readUserUseCase, DeleteUserUseCase deleteUserUseCase) {
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

	public User findByIdEntity(Long id) {
		return readUserUseCase.findByIdEntity(id);
	}
	
	public LoginResponse findByLoginAndPassword(LoginRequest loginRequest) {
		return readUserUseCase.findByLoginAndPassword(loginRequest);
	}


	public UserResponse updateUser(Long id, CreateUserRequest userRequest) {

		return updateUserUseCase.updateUser(id, userRequest);
	}

	public void deleteUser(Long id) {
		deleteUserUseCase.deleteUser(id);
	}

}
