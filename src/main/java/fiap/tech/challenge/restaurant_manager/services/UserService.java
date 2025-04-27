package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest userRequest) {
        // TODO: implementar a validacao de dados
        // TODO: implementar um trycatch para tratamento de exceptions
        User newUser = new User(userRequest);
        return userRepository.save(newUser);
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
