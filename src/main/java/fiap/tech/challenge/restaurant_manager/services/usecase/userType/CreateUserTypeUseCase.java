package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationUserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public CreateUserTypeUseCase(UserTypeRepository userTypeRepository, List<ValidationUserTypeService> validationUserTypeServiceList) {
        this.userTypeRepository = userTypeRepository;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
    }


    public UserTypeResponse createUserType(CreateUserTypeRequest createUserTypeRequest) {

        this.validationUserTypeServiceList.forEach(v -> v.validate(createUserTypeRequest));
        UserType newUserType = new UserType(createUserTypeRequest);
        return toResponse(userTypeRepository.save(newUserType));
    }

    private UserTypeResponse toResponse(UserType userType) {

        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }


}
