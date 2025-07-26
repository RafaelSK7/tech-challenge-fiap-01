package fiap.tech.challenge.restaurant_manager.usecases.userType;


import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.validations.ValidationUserTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public UpdateUserTypeUseCase(UserTypeRepository userTypeRepository, List<ValidationUserTypeService> validationUserTypeServiceList) {
        this.userTypeRepository = userTypeRepository;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
    }

    public UserTypeResponse updateUserType(Long id, CreateUserTypeRequest userTypeRequest) {

        UserType userTypeToUpdate = userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));

        this.validationUserTypeServiceList.forEach(v -> v.validate(userTypeRequest));

        userTypeToUpdate.setUserTypeName(userTypeRequest.userTypeName().trim().toUpperCase());
        userTypeToUpdate.setLastUpdate(LocalDateTime.now());

        return toResponse(userTypeRepository.save(userTypeToUpdate));

    }

    private UserTypeResponse toResponse(UserType userType) {

        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }
}
