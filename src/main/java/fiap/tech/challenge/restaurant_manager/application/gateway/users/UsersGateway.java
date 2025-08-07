package fiap.tech.challenge.restaurant_manager.application.gateway.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.interfaces.users.UsersInterface;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UsersGateway implements UsersInterface {

    private UserRepository userRepository;

    public UsersGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UsersEntity save(CreateUserRequest dto, UserTypesEntity userTypesEntity) {
        UsersEntity newUser = new UsersEntity(dto, userTypesEntity);
        return userRepository.save(newUser);
    }

    @Override
    public Page<UsersEntity> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public Optional<UsersEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(UsersEntity user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<UsersEntity> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public UsersEntity update(UsersEntity userToUpdate) {
        return userRepository.save(userToUpdate);
    }
}
