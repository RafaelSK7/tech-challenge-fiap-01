package fiap.tech.challenge.restaurant_manager.application.validations.impl.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidUserTypeException;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class
EmptyUserTypeNameValidation implements ValidationUserTypeService {

    @Override
    public void validate(CreateUserTypeRequest request) {

        log.info("Validando Nome do user type");
        if ((request.userTypeName() == null) || (request.userTypeName().isEmpty())) {
            throw new InvalidUserTypeException("Nome do tipo de usuario invalido.");
        }

    }
}
