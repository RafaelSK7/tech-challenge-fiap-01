package fiap.tech.challenge.restaurant_manager.application.interfaces.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserTypesInterface {

    UserTypesEntity save(CreateUserTypeRequest dto);

    Page<UserTypesEntity> findAll(Pageable page);

    Optional<UserTypesEntity> findByUserTypeId(Long id);

    void delete(UserTypesEntity userType);

    Optional<UserTypesEntity> findByUserTypeName(String upperCase);

    UserTypesEntity update(UserTypesEntity userTypeToUpdate);
}
