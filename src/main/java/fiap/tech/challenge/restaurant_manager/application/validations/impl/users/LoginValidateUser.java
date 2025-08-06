package fiap.tech.challenge.restaurant_manager.application.validations.impl.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidLoginException;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginValidateUser implements ValidateUserService {

    @Override
    public void validate(CreateUserRequest userRequest) {
        log.info("Validando o login");
        if ((userRequest.login() == null) || (userRequest.login().isEmpty())) {
            throw new InvalidLoginException();
        }

    }
}
