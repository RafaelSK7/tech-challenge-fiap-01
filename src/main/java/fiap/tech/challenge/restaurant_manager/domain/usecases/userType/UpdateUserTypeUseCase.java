package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;


import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UpdateUserTypeUseCase {

    private UserTypesGateway userTypesGateway;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public UpdateUserTypeUseCase(UserTypesGateway userTypesGateway, List<ValidationUserTypeService> validationUserTypeServiceList) {
        this.userTypesGateway = userTypesGateway;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
    }

    public UserTypesEntity updateUserType(Long id, CreateUserTypeRequest userTypeRequest) {
        log.info("Entrou no use case de atualizacao do tipo de usuario.");
        log.info("Buscando tipo de usuario a ser atualizado.");
        UserTypesEntity userTypeToUpdate = userTypesGateway.findByUserTypeId(id).orElseThrow(() -> new UserTypeNotFoundException(id));

        this.validationUserTypeServiceList.forEach(v -> v.validate(userTypeRequest));
        log.info("Populando os campos do tipo de usuario.");
        userTypeToUpdate.setUserTypeName(userTypeRequest.userTypeName().trim().toUpperCase());
        userTypeToUpdate.setLastUpdate(LocalDateTime.now());
        log.info("tipo de usuario populado.");
        return userTypesGateway.update(userTypeToUpdate);
    }

    private UserTypeResponse toResponse(UserTypesEntity userType) {
        log.info("Montando DTO de retorno do tipo de usuario.");
        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }
}
