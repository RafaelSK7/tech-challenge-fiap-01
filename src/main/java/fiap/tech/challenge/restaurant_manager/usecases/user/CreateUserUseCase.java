package fiap.tech.challenge.restaurant_manager.usecases.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.userTypes.UserTypeService;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateUserUseCase {

    private final UserRepository userRepository;

    private UserTypeService userTypeService;

    private List<ValidateUserService> createUserValidations;

    public CreateUserUseCase(UserRepository userRepository, List<ValidateUserService> createUserValidations, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.createUserValidations = createUserValidations;
        this.userTypeService = userTypeService;
    }


    public UserResponse createUser(CreateUserRequest userRequest) {
        log.info("Entrou no use case de criacao do usuario");
        this.createUserValidations.forEach(v -> v.validate(userRequest));
        log.info("Obtém o tipo de usuario.");
        UserType userType = userTypeService.findByIdEntity(userRequest.userTypeId());
        User newUser = new User(userRequest, userType);
        log.info("Criou o usuario.");
        return toResponse(userRepository.save(newUser));
    }

    private UserResponse toResponse(User user) {
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
