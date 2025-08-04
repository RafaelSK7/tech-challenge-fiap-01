package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.usecases.user.DeleteUserUseCase;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    @Test
    void testDeleteUserSuccess() {
        Long userId = 1L;
        User user = UserUtils.getValidUser();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        deleteUserUseCase.deleteUser(userId);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                deleteUserUseCase.deleteUser(userId));
        assertEquals(userId, exception.getMessage());
    }

}
