package fiap.tech.challenge.restaurant_manager.services.userTypes;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.usecases.userType.CreateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.usecases.userType.DeleteUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.usecases.userType.ReadUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.usecases.userType.UpdateUserTypeUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserTypeService {

    private CreateUserTypeUseCase createUserTypeUseCase;
    private UpdateUserTypeUseCase updateUserTypeUseCase;
    private DeleteUserTypeUseCase deleteUserTypeUseCase;
    private ReadUserTypeUseCase readUserTypeUseCase;


    public UserTypeService(CreateUserTypeUseCase createUserTypeUseCase, UpdateUserTypeUseCase updateUserTypeUseCase,
                           ReadUserTypeUseCase readUserTypeUseCase, DeleteUserTypeUseCase deleteUserTypeUseCase) {
        this.createUserTypeUseCase = createUserTypeUseCase;
        this.updateUserTypeUseCase = updateUserTypeUseCase;
        this.readUserTypeUseCase = readUserTypeUseCase;
        this.deleteUserTypeUseCase = deleteUserTypeUseCase;
    }

    public UserTypeResponse createUserType(CreateUserTypeRequest createUserTypeRequest) {
        log.info("Entrou no servico de cadastro do tipo de usuario.");
        return createUserTypeUseCase.createUserType(createUserTypeRequest);

    }

    public Page<UserTypeResponse> findAll(Pageable page) {
        log.info("Entrou no servico de busca de todos os tipos de usuarios.");
        return readUserTypeUseCase.findAll(page);
    }

    public UserTypeResponse findById(Long id) {
        log.info("Entrou no servico de busca do tipo de usuario.");
        return readUserTypeUseCase.findById(id);
    }

    public UserType findByIdEntity(Long id) {
        log.info("Entrou no servico de busca da entidade tipo de usuario.");
        return readUserTypeUseCase.findByIdEntity(id);
    }

    public UserTypeResponse updateUser(Long id, CreateUserTypeRequest userTypeRequest) {
        log.info("Entrou no servico de atualizacao do tipo de usuario.");
        return updateUserTypeUseCase.updateUserType(id, userTypeRequest);
    }

    public void deleteUserType(Long id) {
        log.info("Entrou no servico de remocao do tipo de usuario.");
        deleteUserTypeUseCase.deleteUserType(id);
    }

}
