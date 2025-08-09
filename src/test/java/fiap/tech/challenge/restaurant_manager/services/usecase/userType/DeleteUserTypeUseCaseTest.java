package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.usecases.userType.DeleteUserTypeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserTypeUseCase = new DeleteUserTypeUseCase(userTypeRepository);
    }

    @Test
    void testDeleteUserTypeSuccess() {
        Long id = 1L;
        UserType userType = new UserType();
        userType.setUserTypeId(id);

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));
        doNothing().when(userTypeRepository).delete(userType);

        deleteUserTypeUseCase.deleteUserType(id);

        verify(userTypeRepository, times(1)).findById(id);
        verify(userTypeRepository, times(1)).delete(userType);
    }

    @Test
    void testDeleteUserTypeNotFound() {
        Long id = 2L;
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> deleteUserTypeUseCase.deleteUserType(id));
        verify(userTypeRepository, times(1)).findById(id);
        verify(userTypeRepository, never()).delete(any());
    }

    @Test
    void testDeleteUserTypeDeleteIsCalledWithCorrectEntity() {
        Long id = 3L;
        UserType userType = new UserType();
        userType.setUserTypeId(id);

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));
        doNothing().when(userTypeRepository).delete(any(UserType.class));

        deleteUserTypeUseCase.deleteUserType(id);

        ArgumentCaptor<UserType> captor = ArgumentCaptor.forClass(UserType.class);
        verify(userTypeRepository).delete(captor.capture());
        assertEquals(id, captor.getValue().getUserTypeId());
    }
}