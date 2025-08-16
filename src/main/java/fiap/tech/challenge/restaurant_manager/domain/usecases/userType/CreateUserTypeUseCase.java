package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CreateUserTypeUseCase {

    private UserTypesGateway userTypesGateway;

    private ReadUserTypeUseCase readUserTypeUseCase;

    private List<ValidationUserTypeService> validationUserTypeServiceList;

    public CreateUserTypeUseCase(UserTypesGateway userTypesGateway, List<ValidationUserTypeService> validationUserTypeServiceList, ReadUserTypeUseCase readUserTypeUseCase) {
        this.userTypesGateway = userTypesGateway;
        this.validationUserTypeServiceList = validationUserTypeServiceList;
        this.readUserTypeUseCase = readUserTypeUseCase;
    }


    public UserTypesEntity createUserType(CreateUserTypeRequest createUserTypeRequest) {
        log.info("Entrou no use case de criacao do tipo de usuario");
        this.validationUserTypeServiceList.forEach(v -> v.validate(createUserTypeRequest));
        log.info("Verifica se ja existe algum tipo de usuario com o nome informado.");
        Optional<UserTypesEntity> optionalUserType = readUserTypeUseCase.findDuplicateUserType(createUserTypeRequest.userTypeName().trim().toUpperCase());
        log.info("Se ja existir, lanca excecao.");
        optionalUserType.ifPresent(userType -> {
            throw new DuplicateUserTypeException();
        });

        log.info("Criou o tipo de usuario.");
        return userTypesGateway.save(createUserTypeRequest);
    }
}
