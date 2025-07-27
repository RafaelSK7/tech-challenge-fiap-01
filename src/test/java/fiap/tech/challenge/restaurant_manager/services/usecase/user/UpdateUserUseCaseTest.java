package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.enums.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.utils.AdressUtils;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidateUserService validateUserService;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserUseCase = new UpdateUserUseCase(userRepository, List.of(validateUserService));
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 1L;
        // Cria o request com os dados novos
        CreateUserRequest request = new CreateUserRequest(
                "Novo Nome",
                "novoemail@example.com",
                "novologin",
                "novasenha",
                AdressUtils.getValidCreateAddressRequest(),
                UserType.CLIENT
        );

        doNothing().when(validateUserService).validate(any(CreateUserRequest.class));

        User existingUser = UserUtils.getValidUser();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserResponse response = updateUserUseCase.updateUser(userId, request);

        assertNotNull(response);
        assertEquals("Novo Nome", response.name());
        assertEquals("novoemail@example.com", response.email());
        assertEquals("novologin", response.login());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        CreateUserRequest request = new CreateUserRequest(
                "Novo Nome",
                "novoemail@example.com",
                "novologin",
                "novasenha",
                AdressUtils.getValidCreateAddressRequest(),
                null
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateUserUseCase.updateUser(userId, request));
        assertEquals(userId, exception.getUserId());
    }
}