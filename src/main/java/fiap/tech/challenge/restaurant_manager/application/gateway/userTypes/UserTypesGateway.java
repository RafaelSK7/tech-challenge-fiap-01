package fiap.tech.challenge.restaurant_manager.application.gateway.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.interfaces.UserTypesInterface;
import fiap.tech.challenge.restaurant_manager.domain.entities.userTypes.UserTypes;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserTypesGateway implements UserTypesInterface {

    private UserTypeRepository userTypeRepository;

    public UserTypesGateway (UserTypeRepository userTypeRepository){
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserTypesEntity save(CreateUserTypeRequest dto) {
        UserTypesEntity newUserType = new UserTypesEntity(dto);
        return userTypeRepository.save(newUserType);
    }

    @Override
    public List<UserTypesEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<UserTypesEntity> findById() {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
