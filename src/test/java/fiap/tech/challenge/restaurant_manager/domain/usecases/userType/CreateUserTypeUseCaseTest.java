package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.DuplicateUserTypeException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidationUserTypeService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserTypeUseCaseTest {

    @Mock
    private UserTypesGateway userTypesGateway;

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
                userTypesGateway,
                List.of(validationUserTypeService),
                readUserTypeUseCase
        );
    }

    @Test
    void testCreateUserTypeSuccess() {
        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());

        ArgumentCaptor<UserTypesEntity> userTypeCaptor = ArgumentCaptor.forClass(UserTypesEntity.class);
        ArgumentCaptor<CreateUserTypeRequest> requestCaptor = ArgumentCaptor.forClass(CreateUserTypeRequest.class);

        UserTypesEntity savedUserType = new UserTypesEntity(request);
        savedUserType.setUserTypeId(1L);
        when(userTypesGateway.save(any(CreateUserTypeRequest.class))).thenReturn(savedUserType);

        UserTypesEntity userTypesEntity = createUserTypeUseCase.createUserType(request);

        verify(validationUserTypeService, times(1)).validate(request);
        verify(userTypesGateway, times(1)).save(requestCaptor.capture());
        UserTypesEntity capturedUserType = userTypeCaptor.getValue();

        assertEquals(request.userTypeName().trim().toUpperCase(), capturedUserType.getUserTypeName());
        assertEquals(savedUserType.getUserTypeId(), userTypesEntity.getUserTypeId());
        assertEquals(savedUserType.getUserTypeName(), userTypesEntity.getUserTypeName());
    }

    @Test
    void testCreateUserTypeDuplicate() {
        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        UserTypesEntity existingUserType = new UserTypesEntity(request);
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.of(existingUserType));

        assertThrows(DuplicateUserTypeException.class, () -> createUserTypeUseCase.createUserType(request));
        verify(userTypesGateway, never()).save(any());
    }

    @Test
    void testAllValidationsAreCalled() {
        ValidationUserTypeService validation1 = mock(ValidationUserTypeService.class);
        ValidationUserTypeService validation2 = mock(ValidationUserTypeService.class);

        CreateUserTypeUseCase useCaseWithMultipleValidations = new CreateUserTypeUseCase(
                userTypesGateway,
                List.of(validation1, validation2),
                readUserTypeUseCase
        );

        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());
        UserTypesEntity savedUserType = new UserTypesEntity(request);
        savedUserType.setUserTypeId(1L);
        when(userTypesGateway.save(any(CreateUserTypeRequest.class))).thenReturn(savedUserType);

        useCaseWithMultipleValidations.createUserType(request);

        verify(validation1, times(1)).validate(request);
        verify(validation2, times(1)).validate(request);
    }

    @Test
    void testToResponsePrivateMethodCoverage() throws Exception {

        CreateUserTypeRequest request = new CreateUserTypeRequest("ADMIN");
        doNothing().when(validationUserTypeService).validate(any(CreateUserTypeRequest.class));
        when(readUserTypeUseCase.findDuplicateUserType(any())).thenReturn(Optional.empty());

        UserTypesEntity savedUserType = new UserTypesEntity(request);
        savedUserType.setUserTypeId(99L);
        when(userTypesGateway.save(any(CreateUserTypeRequest.class))).thenReturn(savedUserType);

        UserTypesEntity userTypesEntity = createUserTypeUseCase.createUserType(request);

        assertEquals(99L, userTypesEntity.getUserTypeId());
        assertEquals("ADMIN", userTypesEntity.getUserTypeName());
    }
}