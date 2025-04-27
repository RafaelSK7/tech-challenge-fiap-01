package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.LoginInvalidException;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest userRequest) {
        // TODO: implementar a validacao de dados
        // TODO: implementar um trycatch para tratamento de exceptions
        // TODO: pensar no tipo de response que teremos para cada usuario, por exemplo, criar um DTO UserResponseDTO que retorna apenas email, login, nome e id
        User newUser = new User(userRequest);
        return userRepository.save(newUser);
    }

    public List<User> findAll() {
        // TODO: pensar no tipo de response que teremos para cada usuario, por exemplo, criar um DTO UserResponseDTO que retorna apenas email, login, nome e id
        return userRepository.findAll();
    }

    public User findById(Long id) {
        // TODO: pensar no tipo de response que teremos para cada usuario, por exemplo, criar um DTO UserResponseDTO que retorna apenas email, login, nome e id
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User updateUser(Long id, CreateUserRequest userRequest) {
        User userToUpdate = findById(id);

        userToUpdate.setName(userRequest.name());
        userToUpdate.setEmail(userRequest.email());
        userToUpdate.setLogin(userRequest.login());
        userToUpdate.setPassword(userRequest.password());
        userToUpdate.setAddress(userRequest.address());
        userToUpdate.setUserType(userRequest.userType());
        userToUpdate.setLastUpdate(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        User userToDelete = findById(id);
        userRepository.delete(userToDelete);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(LoginInvalidException::new);
    }
}
