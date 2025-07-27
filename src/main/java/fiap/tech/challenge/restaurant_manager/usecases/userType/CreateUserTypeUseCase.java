package fiap.tech.challenge.restaurant_manager.usecases.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.validations.ValidationUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        log.info("Entrou no use case de criacao do tipo de usuario");
        this.validationUserTypeServiceList.forEach(v -> v.validate(createUserTypeRequest));
        log.info("Verifica se ja existe algum tipo de usuario com o nome informado.");
        Optional<UserType> optionalUserType = readUserTypeUseCase.findDuplicateUserType(createUserTypeRequest.userTypeName().trim().toUpperCase());
        log.info("Se ja existir, lanca excecao.");
        optionalUserType.ifPresent(userType -> {throw new DuplicateUserTypeException();});
        UserType newUserType = new UserType(createUserTypeRequest);
        log.info("Criou o tipo de usuario.");
        return toResponse(userTypeRepository.save(newUserType));
    }

    private UserTypeResponse toResponse(UserType userType) {
        log.info("monta o DTO de retorno.");
        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }


}
