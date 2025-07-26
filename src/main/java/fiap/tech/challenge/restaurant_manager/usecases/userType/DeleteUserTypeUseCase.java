package fiap.tech.challenge.restaurant_manager.usecases.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    public DeleteUserTypeUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public void deleteUserType(Long id) {

        UserType userTypeToDelete = userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));
        userTypeRepository.delete(userTypeToDelete);
    }
}
