package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.services.UserTypeService;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateUserService;

import java.util.Optional;

public class UserTypeValidateUser implements ValidateUserService {

    private UserTypeService userTypeService;

    private UserTypeValidateUser(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @Override
    public void validate(CreateUserRequest request) {

        Optional<UserType> optionalUserType = userTypeService.findByUserTypeId(
                request.userTypeId());

        if (optionalUserType.isEmpty()) {
            throw new UserTypeNotFoundException(request.userTypeId());
        }

    }
}
