package fiap.tech.challenge.restaurant_manager.application.gateway.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.interfaces.UserTypesInterface;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserTypesGateway implements UserTypesInterface {

    private UserTypeRepository userTypeRepository;

    public UserTypesGateway(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserTypesEntity save(CreateUserTypeRequest dto) {
        UserTypesEntity newUserType = new UserTypesEntity(dto);
        return userTypeRepository.save(newUserType);
    }

    @Override
    public Page<UserTypesEntity> findAll(Pageable page) {
        return userTypeRepository.findAll(page);
    }

    @Override
    public Optional<UserTypesEntity> findByUserTypeId(Long id) {
        return userTypeRepository.findByUserTypeId(id);
    }

    @Override
    public void delete(UserTypesEntity userType) {
        userTypeRepository.delete(userType);
    }

    @Override
    public Optional<UserTypesEntity> findByUserTypeName(String userTypeName) {
        return userTypeRepository.findByUserTypeName(userTypeName);
    }

    @Override
    public UserTypesEntity update(UserTypesEntity userTypeToUpdate) {
        return userTypeRepository.save(userTypeToUpdate);
    }

}
