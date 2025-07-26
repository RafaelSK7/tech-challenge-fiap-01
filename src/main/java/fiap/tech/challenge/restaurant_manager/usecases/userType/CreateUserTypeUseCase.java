package fiap.tech.challenge.restaurant_manager.usecases.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.validations.ValidationUserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreateUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    private ReadUserTypeUseCase readUserTypeUseCase;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public CreateUserTypeUseCase(UserTypeRepository userTypeRepository, List<ValidationUserTypeService> validationUserTypeServiceList, ReadUserTypeUseCase readUserTypeUseCase) {
        this.userTypeRepository = userTypeRepository;
        this.readUserTypeUseCase = readUserTypeUseCase;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
    }


    public UserTypeResponse createUserType(CreateUserTypeRequest createUserTypeRequest) {

        this.validationUserTypeServiceList.forEach(v -> v.validate(createUserTypeRequest));

        Optional<UserType> optionalUserType = readUserTypeUseCase.findDuplicateUserType(createUserTypeRequest.userTypeName().trim().toUpperCase());

        optionalUserType.ifPresent(userType -> {throw new DuplicateUserTypeException();});

        UserType newUserType = new UserType(createUserTypeRequest);
        return toResponse(userTypeRepository.save(newUserType));
    }

    private UserTypeResponse toResponse(UserType userType) {

        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }


}
