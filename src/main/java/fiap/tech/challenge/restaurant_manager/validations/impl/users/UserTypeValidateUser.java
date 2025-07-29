package fiap.tech.challenge.restaurant_manager.validations.impl.users;

import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidUserTypeException;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserTypeValidateUser implements ValidateUserService {

    @Override
    public void validate(CreateUserRequest request) {
        log.info("Validando o tipo de usuario");
        if (request.userTypeId() == null) {
            throw new InvalidUserTypeException("Id do tipo de usuario invalido.");
        }

    }
}
