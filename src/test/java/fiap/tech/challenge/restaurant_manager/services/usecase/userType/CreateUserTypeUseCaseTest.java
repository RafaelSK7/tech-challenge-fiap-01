package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.usecases.userType.ReadUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.usecases.userType.CreateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidationUserTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private ValidationUserTypeService validationUserTypeService;

    @Mock
    private ReadUserTypeUseCase readUserTypeUseCase;

    @InjectMocks
    private CreateUserTypeUseCase createUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserTypeUseCase = new CreateUserTypeUseCase(
                userTypeRepository,
                List.of(validationUserTypeService),
                readUserTypeUseCase
        );
    }

    @Test
    void testCreateUserTypeSuccess() {
        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());

        ArgumentCaptor<UserType> userTypeCaptor = ArgumentCaptor.forClass(UserType.class);

        UserType savedUserType = new UserType(request);
        savedUserType.setUserTypeId(1L);
        when(userTypeRepository.save(any(UserType.class))).thenReturn(savedUserType);

        UserTypeResponse response = createUserTypeUseCase.createUserType(request);

        verify(validationUserTypeService, times(1)).validate(request);
        verify(userTypeRepository, times(1)).save(userTypeCaptor.capture());
        UserType capturedUserType = userTypeCaptor.getValue();

        assertEquals(request.userTypeName().trim().toUpperCase(), capturedUserType.getUserTypeName());
        assertEquals(savedUserType.getUserTypeId(), response.id());
        assertEquals(savedUserType.getUserTypeName(), response.userTypeName());
    }

    @Test
    void testCreateUserTypeDuplicate() {
        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        UserType existingUserType = new UserType(request);
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.of(existingUserType));

        assertThrows(DuplicateUserTypeException.class, () -> createUserTypeUseCase.createUserType(request));
        verify(userTypeRepository, never()).save(any());
    }

    @Test
    void testAllValidationsAreCalled() {
        ValidationUserTypeService validation1 = mock(ValidationUserTypeService.class);
        ValidationUserTypeService validation2 = mock(ValidationUserTypeService.class);

        CreateUserTypeUseCase useCaseWithMultipleValidations = new CreateUserTypeUseCase(
                userTypeRepository,
                List.of(validation1, validation2),
                readUserTypeUseCase
        );

        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());
        UserType savedUserType = new UserType(request);
        savedUserType.setUserTypeId(1L);
        when(userTypeRepository.save(any(UserType.class))).thenReturn(savedUserType);

        useCaseWithMultipleValidations.createUserType(request);

        verify(validation1, times(1)).validate(request);
        verify(validation2, times(1)).validate(request);
    }

    @Test
    void testToResponsePrivateMethodCoverage() throws Exception {
        
        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());

        UserType savedUserType = new UserType(request);
        savedUserType.setUserTypeId(99L);
        when(userTypeRepository.save(any(UserType.class))).thenReturn(savedUserType);

        UserTypeResponse response = createUserTypeUseCase.createUserType(request);

        assertEquals(99L, response.id());
        assertEquals("ADMIN", response.userTypeName());
    }
}