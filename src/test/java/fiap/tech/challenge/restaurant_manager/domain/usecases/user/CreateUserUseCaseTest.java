package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
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
    private UsersGateway usersGateway;

    @Mock
    private ValidateUserService validateUserService;


    @InjectMocks
    private CreateUserUseCase createUserUseCase;


    @Mock
    private UserTypeController userTypeController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserUseCase = new CreateUserUseCase(usersGateway, List.of(validateUserService), userTypeController);
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

        ArgumentCaptor<UsersEntity> userCaptor = ArgumentCaptor.forClass(UsersEntity.class);
        ArgumentCaptor<CreateUserRequest> requestCaptor = ArgumentCaptor.forClass(CreateUserRequest.class);


        UsersEntity savedUser = UserUtils.getValidUser();
        when(usersGateway.save(any(CreateUserRequest.class), any(UserTypesEntity.class))).thenReturn(savedUser);

        // Act
        UsersEntity usersEntity = createUserUseCase.createUser(request);

        // Assert
        verify(validateUserService, times(1)).validate(request);
        verify(usersGateway, times(1)).save(requestCaptor.capture(), any(UserTypesEntity.class));
        UsersEntity capturedUser = userCaptor.getValue();

        assertEquals(request.name(), capturedUser.getName());
        assertEquals(request.email(), capturedUser.getEmail());
        assertEquals(request.login(), capturedUser.getLogin());
        assertEquals(savedUser.getId(), usersEntity.getId());
        assertEquals(savedUser.getName(), usersEntity.getName());
        assertEquals(savedUser.getEmail(), usersEntity.getEmail());
        assertEquals(savedUser.getLogin(), usersEntity.getLogin());
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
