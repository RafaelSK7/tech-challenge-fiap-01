package fiap.tech.challenge.restaurant_manager.application.controllers.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.presenters.login.LoginPresenter;
import fiap.tech.challenge.restaurant_manager.application.presenters.users.UserPresenter;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.CreateUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.DeleteUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.ReadUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.UpdateUserUseCase;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserController {

    private CreateUserUseCase createUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private ReadUserUseCase readUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
                          ReadUserUseCase readUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.readUserUseCase = readUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    public UserResponse createUser(CreateUserRequest userRequest) {
        log.info("Entrou no servico de cadastro do usuario.");
        UsersEntity entity = createUserUseCase.createUser(userRequest);
        return UserPresenter.toResponse(entity);

    }

    public Page<UserResponse> findAll(Pageable page) {
        log.info("Entrou no servico de busca de todos os usuarios.");
        return readUserUseCase.findAll(page);
    }

    public UserResponse findById(Long id) {
        log.info("Entrou no servico de busca do usuario.");
        return UserPresenter.toResponse(readUserUseCase.findById(id));
    }

    public UsersEntity findByIdEntity(Long id) {
        log.info("Entrou no servico de busca da entidade usuario.");
        return readUserUseCase.findById(id);
    }

    public LoginResponse findByLoginAndPassword(LoginRequest loginRequest) {
        log.info("Entrou no servico de busca por login e senha.");
        return LoginPresenter.toResponse(readUserUseCase.findByLoginAndPassword(loginRequest));
    }


    public UserResponse updateUser(Long id, CreateUserRequest userRequest) {
        log.info("Entrou no servico de atualizacao do usuario.");
        return updateUserUseCase.updateUser(id, userRequest);
    }

    public void deleteUser(Long id) {
        log.info("Entrou no servico de remocao do usuario.");
        deleteUserUseCase.deleteUser(id);
    }

}
