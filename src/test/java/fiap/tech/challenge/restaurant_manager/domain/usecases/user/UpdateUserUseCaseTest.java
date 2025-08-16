package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateUserService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.utils.AdressUtils;
import fiap.tech.challenge.restaurant_manager.utils.UserTypeUtils;
import fiap.tech.challenge.restaurant_manager.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateUserUseCaseTest {

    @Mock
    private UsersGateway usersGateway;

    @Mock
    private ValidateUserService validateUserService;


    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private UserTypeController userTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserUseCase = new UpdateUserUseCase(usersGateway, List.of(validateUserService), userTypeController);
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
                UserTypeUtils.getValidUserType().getUserTypeId()

        );

        doNothing().when(validateUserService).validate(any(CreateUserRequest.class));

        UsersEntity existingUser = UserUtils.getValidUser();
        existingUser.setId(userId);

        when(usersGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(usersGateway.update(any(UsersEntity.class))).thenReturn(existingUser);
        when(userTypeController.findByIdEntity(request.userTypeId())).thenReturn(new UserTypesEntity(
                1L, "CLIENT", LocalDateTime.now()));

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

        when(usersGateway.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateUserUseCase.updateUser(userId, request));
        assertNotNull(exception.getMessage());

    }
}