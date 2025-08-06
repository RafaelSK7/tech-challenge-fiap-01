package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteUserTypeUseCase {

    private UserTypesGateway userTypesGateway;

    public DeleteUserTypeUseCase(UserTypesGateway userTypesGateway) {
        this.userTypesGateway = userTypesGateway;
    }

    public void deleteUserType(Long id) {
        log.info("Entrou no use case de remocao do tipo de usuario");
        log.info("Buscando tipo de usuario para remocao.");
        UserTypesEntity userTypeToDelete = userTypesGateway.findByUserTypeId(id).orElseThrow(() -> new UserTypeNotFoundException(id));
        log.info("tipo de usuario encontrado.");
        userTypesGateway.delete(userTypeToDelete);
        log.info("tipo de usuario removido com sucesso.");
    }
}
