package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.LoginInvalidException;
import fiap.tech.challenge.restaurant_manager.exceptions.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponse createUser(CreateUserRequest userRequest) {
        // TODO: implementar a validacao de dados
        // TODO: implementar um trycatch para tratamento de exceptions
        User newUser = new User(userRequest);
        return toResponse(userRepository.save(newUser));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserResponse updateUser(Long id, CreateUserRequest userRequest) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userToUpdate.setName(userRequest.name());
        userToUpdate.setEmail(userRequest.email());
        userToUpdate.setLogin(userRequest.login());
        userToUpdate.setPassword(userRequest.password());
        // criar uma controller com uma service para atualizar o endereço updateAddressByUserId
        userToUpdate.setUserType(userRequest.userType());
        userToUpdate.setLastUpdate(LocalDateTime.now());

        return toResponse(userRepository.save(userToUpdate));
    }


    public void updateUserPassword(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(userToDelete);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(LoginInvalidException::new);
    }

    private UserResponse toResponse(User user) {
        AddressResponse addressResponse = null;

        if (user.getAddress() != null) {
            addressResponse = new AddressResponse(
                    user.getAddress().getStreet(),
                    user.getAddress().getNumber(),
                    user.getAddress().getNeighborhood(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getZipCode(),
                    user.getAddress().getCountry()
            );
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getUserType().name(),
                addressResponse
        );
    }
}
