package fiap.tech.challenge.restaurant_manager.application.interfaces;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;

import java.util.List;
import java.util.Optional;

public interface UserTypesInterface {

    UserTypesEntity save(CreateUserTypeRequest dto);

    List<UserTypesEntity> findAll();

    Optional<UserTypesEntity> findById();

    void delete(Long id);

}
