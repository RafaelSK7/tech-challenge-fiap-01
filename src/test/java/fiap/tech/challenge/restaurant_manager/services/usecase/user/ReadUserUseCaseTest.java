package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidLogonException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.usecases.user.ReadUserUseCase;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReadUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReadUserUseCase readUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readUserUseCase = new ReadUserUseCase(userRepository);
    }

    @Test
    void testFindAllUsersSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = UserUtils.getValidUser();
        List<User> users = List.of(user);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserResponse> responsePage = readUserUseCase.findAll(pageable);

        assertNotNull(responsePage);
        assertFalse(responsePage.isEmpty());
        assertEquals(1, responsePage.getContent().size());
        UserResponse response = responsePage.getContent().get(0);
        assertEquals(user.getId(), response.id());
        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());
        assertEquals(user.getLogin(), response.login());
    }

    @Test
    void testFindUserByIdSuccess() {
        Long userId = 1L;
        User user = UserUtils.getValidUser();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = readUserUseCase.findById(userId);

        assertNotNull(response);
        assertEquals(userId, response.id());
        assertEquals(user.getName(), response.name());
    }

    @Test
    void testFindUserByIdNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> readUserUseCase.findById(userId));
        assertEquals(userId, exception.getMessage());
    }

    @Test
    void testFindByLoginAndPasswordSuccess() {
        LoginRequest loginRequest = new LoginRequest("usuario_login", "senha123");
        User user = UserUtils.getValidUser();
        when(userRepository.findByLoginAndPassword(loginRequest.login(), loginRequest.password()))
                .thenReturn(Optional.of(user));

        LoginResponse response = readUserUseCase.findByLoginAndPassword(loginRequest);

        assertNotNull(response);
        assertEquals(user.getId(), response.id());
        assertEquals(user.getName(), response.name());
        assertEquals(user.getLogin(), response.login());
    }

    @Test
    void testFindByLoginAndPasswordFailure() {
        LoginRequest loginRequest = new LoginRequest("usuario_login", "senha123");
        when(userRepository.findByLoginAndPassword(loginRequest.login(), loginRequest.password()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidLogonException.class,
                () -> readUserUseCase.findByLoginAndPassword(loginRequest));
    }


    @Test
    void testFindByIdEntitySuccess() {
        Long userId = 1L;
        User user = UserUtils.getValidUser();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = readUserUseCase.findByIdEntity(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    void testFindByIdEntityNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> readUserUseCase.findByIdEntity(userId));
        assertEquals(userId, exception.getMessage());
    }
}