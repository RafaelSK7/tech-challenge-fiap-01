package fiap.tech.challenge.restaurant_manager.usecases.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.userTypes.UserTypeService;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        this.createUserValidations.forEach(v -> v.validate(userRequest));
        UserType userType = userTypeService.findByIdEntity(userRequest.userTypeId());
        User newUser = new User(userRequest, userType);
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
                user.getUserType().getUserTypeId(), addressResponse, user.getRestaurants());
    }

}
