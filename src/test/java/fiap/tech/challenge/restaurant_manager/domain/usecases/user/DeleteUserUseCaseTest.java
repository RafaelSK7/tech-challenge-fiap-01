package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteUserUseCaseTest {

    @Mock
    private UsersGateway usersGateway;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserUseCase = new DeleteUserUseCase(usersGateway);
    }

    @Test
    void testDeleteUserSuccess() {
        Long userId = 1L;
        UsersEntity user = UserUtils.getValidUser();
        user.setId(userId);

        when(usersGateway.findById(userId)).thenReturn(Optional.of(user));

        deleteUserUseCase.deleteUser(userId);

        verify(usersGateway, times(1)).delete(user);
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;
        when(usersGateway.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                deleteUserUseCase.deleteUser(userId));
        assertNotNull(exception.getMessage());

    }

}
