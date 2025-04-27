package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

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

    public User findById(Long id){
        // TODO: pensar no tipo de response que teremos para cada usuario, por exemplo, criar um DTO UserResponseDTO que retorna apenas email, login, nome e id
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
