package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
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
        UserTypesEntity userTypeToDelete = userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id));
        log.info("tipo de usuario encontrado.");
        userTypeRepository.delete(userTypeToDelete);
        log.info("tipo de usuario removido com sucesso.");
    }
}
