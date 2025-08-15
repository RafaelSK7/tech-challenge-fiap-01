package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateUserTypeUseCaseTest {

    @Mock
    private UserTypesGateway userTypesGateway;

    @Mock
    private ValidationUserTypeService validationUserTypeService;

    @InjectMocks
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserTypeUseCase = new UpdateUserTypeUseCase(
                userTypesGateway,
                List.of(validationUserTypeService)
        );
    }

    @Test
    void testUpdateUserTypeSuccess() {
        Long id = 1L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("GERENTE");
        UserTypesEntity existingUserType = new UserTypesEntity();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("ADMIN");

        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(existingUserType));

        UserTypesEntity updatedUserType = new UserTypesEntity();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("GERENTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypesGateway.update(any(UserTypesEntity.class))).thenReturn(updatedUserType);

        UserTypesEntity userTypesEntity = updateUserTypeUseCase.updateUserType(id, request);

        verify(validationUserTypeService, times(1)).validate(request);
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
        verify(userTypesGateway, times(1)).update(any(UserTypesEntity.class));

        assertEquals(id, userTypesEntity.getUserTypeId());
        assertEquals("GERENTE", userTypesEntity.getUserTypeName());
    }

    @Test
    void testUpdateUserTypeNotFound() {
        Long id = 2L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("GERENTE");

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> updateUserTypeUseCase.updateUserType(id, request));
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
        verify(userTypesGateway, never()).save(any());
    }

    @Test
    void testAllValidationsAreCalled() {
        ValidationUserTypeService validation1 = mock(ValidationUserTypeService.class);
        ValidationUserTypeService validation2 = mock(ValidationUserTypeService.class);

        UpdateUserTypeUseCase useCaseWithMultipleValidations = new UpdateUserTypeUseCase(
                userTypesGateway,
                List.of(validation1, validation2)
        );

        Long id = 3L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("SUPORTE");
        UserTypesEntity existingUserType = new UserTypesEntity();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("CLIENTE");

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(existingUserType));
        UserTypesEntity updatedUserType = new UserTypesEntity();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("SUPORTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypesGateway.save(any(CreateUserTypeRequest.class))).thenReturn(updatedUserType);

        useCaseWithMultipleValidations.updateUserType(id, request);

        verify(validation1, times(1)).validate(request);
        verify(validation2, times(1)).validate(request);
    }

    @Test
    void testToResponsePrivateMethodCoverage() {

        Long id = 4L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("SUPORTE");
        UserTypesEntity existingUserType = new UserTypesEntity();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("CLIENTE");

        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(existingUserType));

        UserTypesEntity updatedUserType = new UserTypesEntity();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("SUPORTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypesGateway.update(any(UserTypesEntity.class))).thenReturn(updatedUserType);

        UserTypesEntity userTypesEntity = updateUserTypeUseCase.updateUserType(id, request);

        assertEquals(id, userTypesEntity.getUserTypeId());
        assertEquals("SUPORTE", userTypesEntity.getUserTypeName());
    }
}