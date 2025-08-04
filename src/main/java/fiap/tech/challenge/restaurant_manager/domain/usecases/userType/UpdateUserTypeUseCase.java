package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;


import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UpdateUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public UpdateUserTypeUseCase(UserTypeRepository userTypeRepository, List<ValidationUserTypeService> validationUserTypeServiceList) {
        this.userTypeRepository = userTypeRepository;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
    }

    public UserTypeResponse updateUserType(Long id, CreateUserTypeRequest userTypeRequest) {
        log.info("Entrou no use case de atualizacao do tipo de usuario.");
        log.info("Buscando tipo de usuario a ser atualizado.");
        UserTypesEntity userTypeToUpdate = userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));

        this.validationUserTypeServiceList.forEach(v -> v.validate(userTypeRequest));
        log.info("Populando os campos do tipo de usuario.");
        userTypeToUpdate.setUserTypeName(userTypeRequest.userTypeName().trim().toUpperCase());
        userTypeToUpdate.setLastUpdate(LocalDateTime.now());
        log.info("tipo de usuario populado.");
        return toResponse(userTypeRepository.save(userTypeToUpdate));

    }

    private UserTypeResponse toResponse(UserTypesEntity userType) {
        log.info("Montando DTO de retorno do tipo de usuario.");
        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }
}
