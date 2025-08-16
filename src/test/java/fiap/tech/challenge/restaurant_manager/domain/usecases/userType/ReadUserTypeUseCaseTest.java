package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReadUserTypeUseCaseTest {

    @Mock
    private UserTypesGateway userTypesGateway;

    @InjectMocks
    private ReadUserTypeUseCase readUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readUserTypeUseCase = new ReadUserTypeUseCase(userTypesGateway);
    }

    @Test
    void testFindAllReturnsPageOfUserTypeResponse() {
        Pageable pageable = PageRequest.of(0, 2);
        UserTypesEntity userType1 = new UserTypesEntity();
        userType1.setUserTypeId(1L);
        userType1.setUserTypeName("ADMIN");
        UserTypesEntity userType2 = new UserTypesEntity();
        userType2.setUserTypeId(2L);
        userType2.setUserTypeName("CLIENTE");

        List<UserTypesEntity> userTypes = List.of(userType1, userType2);
        Page<UserTypesEntity> userTypePage = new PageImpl<>(userTypes, pageable, userTypes.size());

        when(userTypesGateway.findAll(pageable)).thenReturn(userTypePage);

        Page<UserTypeResponse> responsePage = readUserTypeUseCase.findAll(pageable);

        assertEquals(2, responsePage.getTotalElements());
        assertEquals("ADMIN", responsePage.getContent().get(0).userTypeName());
        assertEquals("CLIENTE", responsePage.getContent().get(1).userTypeName());
        verify(userTypesGateway, times(1)).findAll(pageable);
    }

    @Test
    void testFindByIdReturnsUserTypeResponse() {
        Long id = 1L;
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(id);
        userType.setUserTypeName("ADMIN");

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(userType));

        UserTypesEntity userTypesEntity = readUserTypeUseCase.findByUserTypeId(id);

        assertEquals(id, userTypesEntity.getUserTypeId());
        assertEquals("ADMIN", userTypesEntity.getUserTypeName());
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindByIdThrowsUserTypeNotFoundException() {
        Long id = 2L;
        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> readUserTypeUseCase.findByUserTypeId(id));
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindByIdEntityReturnsUserType() {
        Long id = 3L;
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(id);
        userType.setUserTypeName("CLIENTE");

        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.of(userType));

        UserTypesEntity result = readUserTypeUseCase.findByIdEntity(id);

        assertEquals(id, result.getUserTypeId());
        assertEquals("CLIENTE", result.getUserTypeName());
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindByIdEntityThrowsUserTypeNotFoundException() {
        Long id = 4L;
        when(userTypesGateway.findByUserTypeId(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> readUserTypeUseCase.findByIdEntity(id));
        verify(userTypesGateway, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindDuplicateUserTypeReturnsOptionalUserType() {
        String name = "ADMIN";
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(1L);
        userType.setUserTypeName("ADMIN");

        when(userTypesGateway.findByUserTypeName(name.trim().toUpperCase())).thenReturn(Optional.of(userType));

        Optional<UserTypesEntity> result = readUserTypeUseCase.findDuplicateUserType(name);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getUserTypeName());
        verify(userTypesGateway, times(1)).findByUserTypeName("ADMIN");
    }

    @Test
    void testFindDuplicateUserTypeReturnsEmptyOptional() {
        String name = "CLIENTE";
        when(userTypesGateway.findByUserTypeName(name.trim().toUpperCase())).thenReturn(Optional.empty());

        Optional<UserTypesEntity> result = readUserTypeUseCase.findDuplicateUserType(name);

        assertTrue(result.isEmpty());
        verify(userTypesGateway, times(1)).findByUserTypeName("CLIENTE");
    }
}