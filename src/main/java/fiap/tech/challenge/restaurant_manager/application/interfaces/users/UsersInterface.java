package fiap.tech.challenge.restaurant_manager.application.interfaces.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsersInterface {

    UsersEntity save(CreateUserRequest dto, UserTypesEntity userTypesEntity);

    Page<UsersEntity> findAll(Pageable page);

    Optional<UsersEntity> findById(Long id);

    void delete(UsersEntity user);

    Optional<UsersEntity> findByLoginAndPassword(String login, String password);

    UsersEntity update(UsersEntity userToUpdate);
}
