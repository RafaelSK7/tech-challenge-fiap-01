package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateUserUseCase {

    private final UserRepository userRepository;

    private UserTypeController userTypeService;

    private List<ValidateUserService> createUserValidations;

    public CreateUserUseCase(UserRepository userRepository, List<ValidateUserService> createUserValidations, UserTypeController userTypeService) {
        this.userRepository = userRepository;
        this.createUserValidations = createUserValidations;
        this.userTypeService = userTypeService;
    }


    public UserResponse createUser(CreateUserRequest userRequest) {
        log.info("Entrou no use case de criacao do usuario");
        this.createUserValidations.forEach(v -> v.validate(userRequest));
        log.info("Obtém o tipo de usuario.");
        UserTypesEntity userType = userTypeService.findByIdEntity(userRequest.userTypeId());
        UsersEntity newUser = new UsersEntity(userRequest, userType);
        log.info("Criou o usuario.");
        return toResponse(userRepository.save(newUser));
    }

    private UserResponse toResponse(UsersEntity user) {
        log.info("monta o DTO de retorno.");
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
