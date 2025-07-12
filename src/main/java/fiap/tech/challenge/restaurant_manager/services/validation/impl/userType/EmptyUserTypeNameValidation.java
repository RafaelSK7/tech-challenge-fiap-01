package fiap.tech.challenge.restaurant_manager.services.validation.impl.userType;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidUserTypeException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmptyUserTypeNameValidation implements ValidationUserTypeService {

    @Override
    public void validate(CreateUserTypeRequest request) {

        log.info("Validando Nome do user type");
        if ((request.userTypeName() == null) || (request.userTypeName().isEmpty())) {
            throw new InvalidUserTypeException();
        }

    }
}
