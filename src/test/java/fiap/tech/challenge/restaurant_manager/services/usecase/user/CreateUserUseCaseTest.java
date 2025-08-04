package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.userTypes.UserTypeService;
import fiap.tech.challenge.restaurant_manager.usecases.user.CreateUserUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.utils.UserTypeUtils;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidCreateAddressRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidateUserService validateUserService;

    @Mock
    private UserTypeService userTypeService;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserUseCase = new CreateUserUseCase(userRepository, List.of(validateUserService), userTypeService);
    }

    @Test
    void testCreateUserSuccess() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Nome do Usuário",
                "usuario@example.com",
                "usuario_login",
                "USER",
                getValidCreateAddressRequest(),
                UserTypeUtils.getValidUserType().getUserTypeId()
        );
        doNothing().when(validateUserService).validate(any(CreateUserRequest.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        User savedUser = UserUtils.getValidUser();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = createUserUseCase.createUser(request);

        // Assert
        verify(validateUserService, times(1)).validate(request);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(request.name(), capturedUser.getName());
        assertEquals(request.email(), capturedUser.getEmail());
        assertEquals(request.login(), capturedUser.getLogin());
        assertEquals(savedUser.getId(), response.id());
        assertEquals(savedUser.getName(), response.name());
        assertEquals(savedUser.getEmail(), response.email());
        assertEquals(savedUser.getLogin(), response.login());
    }

    @Test
    void testCreateUserValidationFailure() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Nome do Usuário",
                "usuario@example.com",
                "usuario_login",
                "USER",
                getValidCreateAddressRequest(),
                UserTypeUtils.getValidUserType().getUserTypeId()
        );
        doThrow(new IllegalArgumentException("Validação falhou")).when(validateUserService).validate(any(CreateUserRequest.class));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createUserUseCase.createUser(request));
        assertEquals("Validação falhou", exception.getMessage());
    }
}
