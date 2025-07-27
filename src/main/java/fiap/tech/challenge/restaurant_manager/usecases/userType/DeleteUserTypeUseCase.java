package fiap.tech.challenge.restaurant_manager.usecases.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    public DeleteUserTypeUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public void deleteUserType(Long id) {
        log.info("Entrou no use case de remocao do tipo de usuario");
        log.info("Buscando tipo de usuario para remocao.");
        UserType userTypeToDelete = userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));
        log.info("tipo de usuario encontrado.");
        userTypeRepository.delete(userTypeToDelete);
        log.info("tipo de usuario removido com sucesso.");
    }
}
