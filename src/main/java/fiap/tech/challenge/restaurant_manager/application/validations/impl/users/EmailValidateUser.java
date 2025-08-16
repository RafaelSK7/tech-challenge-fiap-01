package fiap.tech.challenge.restaurant_manager.application.validations.impl.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidEmailException;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailValidateUser implements ValidateUserService {

    private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,6}$";

    @Override
    public void validate(CreateUserRequest userRequest) {
        log.info("Validando email");
        if ((userRequest.email() == null) || (userRequest.email().isEmpty())
                || (!userRequest.email().matches(EMAIL_REGEX))) {
            throw new InvalidEmailException();
        }
    }
}
