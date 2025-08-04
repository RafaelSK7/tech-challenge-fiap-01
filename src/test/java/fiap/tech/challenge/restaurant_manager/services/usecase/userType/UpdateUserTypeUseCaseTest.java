package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.usecases.userType.UpdateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidationUserTypeService;
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
import static org.mockito.Mockito.*;

public class UpdateUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private ValidationUserTypeService validationUserTypeService;

    @InjectMocks
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserTypeUseCase = new UpdateUserTypeUseCase(
                userTypeRepository,
                List.of(validationUserTypeService)
        );
    }

    @Test
    void testUpdateUserTypeSuccess() {
        Long id = 1L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("GERENTE");
        UserType existingUserType = new UserType();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("ADMIN");

        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existingUserType));

        UserType updatedUserType = new UserType();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("GERENTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypeRepository.save(any(UserType.class))).thenReturn(updatedUserType);

        UserTypeResponse response = updateUserTypeUseCase.updateUserType(id, request);

        verify(validationUserTypeService, times(1)).validate(request);
        verify(userTypeRepository, times(1)).findById(id);
        verify(userTypeRepository, times(1)).save(any(UserType.class));
        assertEquals(id, response.id());
        assertEquals("GERENTE", response.userTypeName());
    }

    @Test
    void testUpdateUserTypeNotFound() {
        Long id = 2L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("GERENTE");

        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> updateUserTypeUseCase.updateUserType(id, request));
        verify(userTypeRepository, times(1)).findById(id);
        verify(userTypeRepository, never()).save(any());
    }

    @Test
    void testAllValidationsAreCalled() {
        ValidationUserTypeService validation1 = mock(ValidationUserTypeService.class);
        ValidationUserTypeService validation2 = mock(ValidationUserTypeService.class);

        UpdateUserTypeUseCase useCaseWithMultipleValidations = new UpdateUserTypeUseCase(
                userTypeRepository,
                List.of(validation1, validation2)
        );

        Long id = 3L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("SUPORTE");
        UserType existingUserType = new UserType();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("CLIENTE");

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existingUserType));
        UserType updatedUserType = new UserType();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("SUPORTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypeRepository.save(any(UserType.class))).thenReturn(updatedUserType);

        useCaseWithMultipleValidations.updateUserType(id, request);

        verify(validation1, times(1)).validate(request);
        verify(validation2, times(1)).validate(request);
    }

    @Test
    void testToResponsePrivateMethodCoverage() {
        
        Long id = 4L;
        CreateUserTypeRequest request = new CreateUserTypeRequest("SUPORTE");
        UserType existingUserType = new UserType();
        existingUserType.setUserTypeId(id);
        existingUserType.setUserTypeName("CLIENTE");

        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existingUserType));

        UserType updatedUserType = new UserType();
        updatedUserType.setUserTypeId(id);
        updatedUserType.setUserTypeName("SUPORTE");
        updatedUserType.setLastUpdate(LocalDateTime.now());
        when(userTypeRepository.save(any(UserType.class))).thenReturn(updatedUserType);

        UserTypeResponse response = updateUserTypeUseCase.updateUserType(id, request);

        assertEquals(id, response.id());
        assertEquals("SUPORTE", response.userTypeName());
    }
}