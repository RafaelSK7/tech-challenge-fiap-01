package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateUserUseCase {

    private final UsersGateway usersGateway;

    private UserTypeController userTypeController;

    private List<ValidateUserService> createUserValidations;

    public CreateUserUseCase(UsersGateway usersGateway, List<ValidateUserService> createUserValidations, UserTypeController userTypeController) {
        this.usersGateway = usersGateway;
        this.createUserValidations = createUserValidations;
        this.userTypeController = userTypeController;
    }

    public UsersEntity createUser(CreateUserRequest userRequest) {
        log.info("Entrou no use case de criacao do usuario");
        this.createUserValidations.forEach(v -> v.validate(userRequest));
        log.info("Obtém o tipo de usuario.");
        UserTypesEntity userType = userTypeController.findByIdEntity(userRequest.userTypeId());
        log.info("Criou o usuario.");
        return usersGateway.save(userRequest, userType);
    }

}
