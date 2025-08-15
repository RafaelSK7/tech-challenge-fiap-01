package fiap.tech.challenge.restaurant_manager.application.controller.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.domain.usecases.userType.CreateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.userType.DeleteUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.userType.ReadUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.userType.UpdateUserTypeUseCase;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTypeControllerTest {

    @InjectMocks
    private UserTypeController userTypeController;

    @Mock
    private CreateUserTypeUseCase createUserTypeUseCase;

    @Mock
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @Mock
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @Mock
    private ReadUserTypeUseCase readUserTypeUseCase;

    private UserTypesEntity mockEntity;
    private CreateUserTypeRequest mockRequest;
    private UserTypeResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockEntity = new UserTypesEntity();
        mockEntity.setUserTypeId(1L);
        mockEntity.setUserTypeName("OWNER");

        mockRequest = new CreateUserTypeRequest("OWNER");
        mockResponse = new UserTypeResponse(1L, "OWNER");
    }

    @Test
    @DisplayName("createUserType - Deve criar um userTypeName de usuário com sucesso e retornar a resposta DTO")
    void deve_criarUserType_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(createUserTypeUseCase.createUserType(any(CreateUserTypeRequest.class))).thenReturn(mockEntity);

        // Ação: Chamar o método do controller
        UserTypeResponse actualResponse = userTypeController.createUserType(mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.userTypeName(), actualResponse.userTypeName());
        verify(createUserTypeUseCase).createUserType(mockRequest);
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de userTypeNames de usuários")
    void deve_encontrarTodosUserTypes_quandoSolicitado() {
        // Cenário: Mock do use case para retornar uma página paginada
        Page<UserTypeResponse> expectedPage = new PageImpl<>(Collections.singletonList(mockResponse));
        when(readUserTypeUseCase.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chamar o método do controller com um objeto Pageable
        Page<UserTypeResponse> actualPage = userTypeController.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockResponse.userTypeName(), actualPage.getContent().get(0).userTypeName());
        verify(readUserTypeUseCase).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve encontrar um userTypeName de usuário por ID e retornar a resposta DTO")
    void deve_encontrarUserTypePorId_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(readUserTypeUseCase.findByUserTypeId(anyLong())).thenReturn(mockEntity);

        // Ação: Chamar o método do controller com o ID
        UserTypeResponse actualResponse = userTypeController.findById(1L);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        assertEquals(mockResponse.userTypeName(), actualResponse.userTypeName());
        verify(readUserTypeUseCase).findByUserTypeId(1L);
    }

    @Test
    @DisplayName("findByIdEntity - Deve encontrar um userTypeName de usuário por ID e retornar a entidade")
    void deve_encontrarUserTypeEntityPorId_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(readUserTypeUseCase.findByIdEntity(anyLong())).thenReturn(mockEntity);

        // Ação: Chamar o método do controller
        UserTypesEntity actualEntity = userTypeController.findByIdEntity(1L);

        // Verificação:
        assertNotNull(actualEntity);
        assertEquals(mockEntity.getUserTypeId(), actualEntity.getUserTypeId());
        assertEquals(mockEntity.getUserTypeName(), actualEntity.getUserTypeName());
        verify(readUserTypeUseCase).findByIdEntity(1L);
    }

    @Test
    @DisplayName("updateUser - Deve atualizar um userTypeName de usuário e retornar a resposta DTO")
    void deve_atualizarUserType_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade atualizada
        UserTypesEntity updatedEntity = new UserTypesEntity();
        updatedEntity.setUserTypeId(1L);
        updatedEntity.setUserTypeName("USER");
        when(updateUserTypeUseCase.updateUserType(anyLong(), any(CreateUserTypeRequest.class))).thenReturn(updatedEntity);

        // Ação: Chamar o método do controller
        UserTypeResponse actualResponse = userTypeController.updateUser(1L, mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(updatedEntity.getUserTypeId(), actualResponse.id());
        assertEquals(updatedEntity.getUserTypeName(), actualResponse.userTypeName());
        verify(updateUserTypeUseCase).updateUserType(1L, mockRequest);
    }

    @Test
    @DisplayName("deleteUserType - Deve deletar um userTypeName de usuário com sucesso")
    void deve_deletarUserType_quandoSolicitado() {
        // Cenário: Mock do use case para não fazer nada
        doNothing().when(deleteUserTypeUseCase).deleteUserType(anyLong());

        // Ação: Chamar o método do controller
        userTypeController.deleteUserType(1L);

        // Verificação: Garantir que o método do use case foi chamado
        verify(deleteUserTypeUseCase).deleteUserType(1L);
    }
}