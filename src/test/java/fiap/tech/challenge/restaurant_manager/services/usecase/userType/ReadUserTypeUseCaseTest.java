package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import fiap.tech.challenge.restaurant_manager.usecases.userType.ReadUserTypeUseCase;
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
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private ReadUserTypeUseCase readUserTypeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readUserTypeUseCase = new ReadUserTypeUseCase(userTypeRepository);
    }

    @Test
    void testFindAllReturnsPageOfUserTypeResponse() {
        Pageable pageable = PageRequest.of(0, 2);
        UserType userType1 = new UserType();
        userType1.setUserTypeId(1L);
        userType1.setUserTypeName("ADMIN");
        UserType userType2 = new UserType();
        userType2.setUserTypeId(2L);
        userType2.setUserTypeName("CLIENTE");

        List<UserType> userTypes = List.of(userType1, userType2);
        Page<UserType> userTypePage = new PageImpl<>(userTypes, pageable, userTypes.size());

        when(userTypeRepository.findAll(pageable)).thenReturn(userTypePage);

        Page<UserTypeResponse> responsePage = readUserTypeUseCase.findAll(pageable);

        assertEquals(2, responsePage.getTotalElements());
        assertEquals("ADMIN", responsePage.getContent().get(0).userTypeName());
        assertEquals("CLIENTE", responsePage.getContent().get(1).userTypeName());
        verify(userTypeRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindByIdReturnsUserTypeResponse() {
        Long id = 1L;
        UserType userType = new UserType();
        userType.setUserTypeId(id);
        userType.setUserTypeName("ADMIN");

        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));

        UserTypeResponse response = readUserTypeUseCase.findById(id);

        assertEquals(id, response.id());
        assertEquals("ADMIN", response.userTypeName());
        verify(userTypeRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsUserTypeNotFoundException() {
        Long id = 2L;
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> readUserTypeUseCase.findById(id));
        verify(userTypeRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdEntityReturnsUserType() {
        Long id = 3L;
        UserType userType = new UserType();
        userType.setUserTypeId(id);
        userType.setUserTypeName("CLIENTE");

        when(userTypeRepository.findByUserTypeId(id)).thenReturn(Optional.of(userType));

        UserType result = readUserTypeUseCase.findByIdEntity(id);

        assertEquals(id, result.getUserTypeId());
        assertEquals("CLIENTE", result.getUserTypeName());
        verify(userTypeRepository, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindByIdEntityThrowsUserTypeNotFoundException() {
        Long id = 4L;
        when(userTypeRepository.findByUserTypeId(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> readUserTypeUseCase.findByIdEntity(id));
        verify(userTypeRepository, times(1)).findByUserTypeId(id);
    }

    @Test
    void testFindDuplicateUserTypeReturnsOptionalUserType() {
        String name = "ADMIN";
        UserType userType = new UserType();
        userType.setUserTypeId(1L);
        userType.setUserTypeName("ADMIN");

        when(userTypeRepository.findByUserTypeName(name.trim().toUpperCase())).thenReturn(Optional.of(userType));

        Optional<UserType> result = readUserTypeUseCase.findDuplicateUserType(name);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getUserTypeName());
        verify(userTypeRepository, times(1)).findByUserTypeName("ADMIN");
    }

    @Test
    void testFindDuplicateUserTypeReturnsEmptyOptional() {
        String name = "CLIENTE";
        when(userTypeRepository.findByUserTypeName(name.trim().toUpperCase())).thenReturn(Optional.empty());

        Optional<UserType> result = readUserTypeUseCase.findDuplicateUserType(name);

        assertTrue(result.isEmpty());
        verify(userTypeRepository, times(1)).findByUserTypeName("CLIENTE");
    }
}