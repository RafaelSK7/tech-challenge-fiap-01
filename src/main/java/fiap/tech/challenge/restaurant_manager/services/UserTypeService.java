package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.services.usecase.userType.CreateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.userType.DeleteUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.userType.ReadUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.userType.UpdateUserTypeUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        return createUserTypeUseCase.createUserType(createUserTypeRequest);

    }

    public Page<UserTypeResponse> findAll(Pageable page) {
        return readUserTypeUseCase.findAll(page);
    }

    public UserTypeResponse findById(Long id) {
        return readUserTypeUseCase.findById(id);
    }

    public Optional<UserType> findByUserTypeId(Long id) {
        return readUserTypeUseCase.findUserTypeById(id);
    }

    public UserTypeResponse updateUser(Long id, CreateUserTypeRequest userTypeRequest) {

        return updateUserTypeUseCase.updateUserType(id, userTypeRequest);
    }

    public void deleteUserType(Long id) {
        deleteUserTypeUseCase.deleteUserType(id);
    }

}
