package fiap.tech.challenge.restaurant_manager.services.validation.impl.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.services.UserTypeService;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DuplicateUserTypeNameValidation implements ValidationUserTypeService {

    private UserTypeService userTypeService;

    @Override
    public void validate(CreateUserTypeRequest request) {

        Optional<UserType> optionalUserType = userTypeService.findDuplicateUserTypeByName(request.userTypeName().trim());

        if(optionalUserType.isPresent()){
            throw new DuplicateUserTypeException();
        }

    }
}
