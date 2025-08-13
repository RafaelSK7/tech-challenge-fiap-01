package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidLogonException;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReadUserUseCaseTest {

    @Mock
    private UsersGateway usersGateway;

    @InjectMocks
    private ReadUserUseCase readUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readUserUseCase = new ReadUserUseCase(usersGateway);
    }

    @Test
    void testFindAllUsersSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        UsersEntity user = UserUtils.getValidUser();
        List<UsersEntity> users = List.of(user);
        Page<UsersEntity> userPage = new PageImpl<>(users, pageable, users.size());

        when(usersGateway.findAll(pageable)).thenReturn(userPage);

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
        UsersEntity user = UserUtils.getValidUser();
        user.setId(userId);

        when(usersGateway.findById(userId)).thenReturn(Optional.of(user));

        UsersEntity usersEntity = readUserUseCase.findById(userId);

        assertNotNull(usersEntity);
        assertEquals(userId, usersEntity.getId());
        assertEquals(user.getName(), usersEntity.getName());
    }

    @Test
    void testFindUserByIdNotFound() {
        Long userId = 1L;
        when(usersGateway.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> readUserUseCase.findById(userId));
        assertEquals(any(), exception.getMessage());

    }

    @Test
    void testFindByLoginAndPasswordSuccess() {
        LoginRequest loginRequest = new LoginRequest("usuario_login", "senha123");
        UsersEntity user = UserUtils.getValidUser();
        when(usersGateway.findByLoginAndPassword(loginRequest.login(), loginRequest.password()))
                .thenReturn(Optional.of(user));

        UsersEntity loggedUser = readUserUseCase.findByLoginAndPassword(loginRequest);

        assertNotNull(loggedUser);
        assertEquals(user.getId(), loggedUser.getId());
        assertEquals(user.getName(), loggedUser.getName());
        assertEquals(user.getLogin(), loggedUser.getLogin());
    }

    @Test
    void testFindByLoginAndPasswordFailure() {
        LoginRequest loginRequest = new LoginRequest("usuario_login", "senha123");
        when(usersGateway.findByLoginAndPassword(loginRequest.login(), loginRequest.password()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidLogonException.class,
                () -> readUserUseCase.findByLoginAndPassword(loginRequest));
    }


    @Test
    void testFindByIdEntitySuccess() {
        Long userId = 1L;
        UsersEntity user = UserUtils.getValidUser();
        user.setId(userId);

        when(usersGateway.findById(userId)).thenReturn(Optional.of(user));

        UsersEntity foundUser = readUserUseCase.findById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    void testFindByIdEntityNotFound() {
        Long userId = 1L;
        when(usersGateway.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> readUserUseCase.findById(userId));
        assertEquals(any(), exception.getMessage());

    }
}