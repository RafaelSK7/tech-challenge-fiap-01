package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteUserTypeUseCaseTest {

    @Mock
    private UserTypesGateway userTypesGateway;

    @InjectMocks
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserTypeUseCase = new DeleteUserTypeUseCase(userTypesGateway);
    }

    @Test
    void testDeleteUserTypeSuccess() {
        Long id = 1L;
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(id);

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(userType));
        doNothing().when(userTypesGateway).delete(userType);

        deleteUserTypeUseCase.deleteUserType(id);

        verify(userTypesGateway, times(1)).findByUserTypeId(id);
        verify(userTypesGateway, times(1)).delete(userType);
    }

    @Test
    void testDeleteUserTypeNotFound() {
        Long id = 2L;
        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> deleteUserTypeUseCase.deleteUserType(id));
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
        verify(userTypesGateway, never()).delete(any());
    }

    @Test
    void testDeleteUserTypeDeleteIsCalledWithCorrectEntity() {
        Long id = 3L;
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(id);

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(userType));
        doNothing().when(userTypesGateway).delete(any(UserTypesEntity.class));

        deleteUserTypeUseCase.deleteUserType(id);

        ArgumentCaptor<UserTypesEntity> captor = ArgumentCaptor.forClass(UserTypesEntity.class);
        verify(userTypesGateway).delete(captor.capture());
        assertEquals(id, captor.getValue().getUserTypeId());
    }
}