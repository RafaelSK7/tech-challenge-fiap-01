package fiap.tech.challenge.restaurant_manager.application.controller.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.CreateUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.DeleteUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.ReadUserUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.user.UpdateUserUseCase;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
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

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private ReadUserUseCase readUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    private UsersEntity mockEntity;
    private CreateUserRequest mockRequest;
    private UserResponse mockResponse;
    private LoginRequest mockLoginRequest;
    private CreateAddressRequest addressRequest;
    private AddressResponse addressResponse;
    private UserTypesEntity mockUserTypes;
    private AddressEntity mockAddress;

    @BeforeEach
    void setUp() {
        // Inicialização de objetos mock para evitar duplicação de código.
        mockUserTypes = new UserTypesEntity();
        mockUserTypes.setUserTypeId(1L);
        mockUserTypes.setUserTypeName("CLIENT");

        mockAddress = new AddressEntity();
        mockAddress.setId(1L);
        mockAddress.setStreet("Rua A");
        mockAddress.setNumber("2 B");
        mockAddress.setNeighborhood("São Miguel");
        mockAddress.setCity("Sao Paulo");
        mockAddress.setState("SP");
        mockAddress.setZipCode("09876543");
        mockAddress.setCountry("Brasil");

        mockEntity = new UsersEntity();
        mockEntity.setId(1L);
        mockEntity.setName("João");
        mockEntity.setEmail("joao@teste.com");
        mockEntity.setUserType(mockUserTypes);
        mockEntity.setAddress(mockAddress);

        addressRequest = new CreateAddressRequest("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil");
        addressResponse = new AddressResponse("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil");

        mockRequest = new CreateUserRequest("João", "joao@teste.com", "senha123", "12345678901", addressRequest, 1L);
        mockResponse = new UserResponse(1L, "João", "joao@teste.com", "12345678901", 1L, addressResponse, new ArrayList<>());
        mockLoginRequest = new LoginRequest("usuario@teste.com", "senha123");
    }

    @Test
    @DisplayName("createUser - Deve criar um usuário e retornar a resposta DTO")
    void deve_criarUsuario_quandoSolicitado() {
        // Cenário: Mocka o use case para retornar a entidade esperada.
        when(createUserUseCase.createUser(any(CreateUserRequest.class))).thenReturn(mockEntity);

        // Ação: Chama o método do controller.
        UserResponse actualResponse = userController.createUser(mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        verify(createUserUseCase).createUser(mockRequest);
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de usuários")
    void deve_encontrarTodosUsuarios_quandoSolicitado() {
        // Cenário: Mocka o use case para retornar uma página paginada.
        Page<UserResponse> expectedPage = new PageImpl<>(Collections.singletonList(mockResponse));
        when(readUserUseCase.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chama o método do controller com um objeto Pageable.
        Page<UserResponse> actualPage = userController.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockResponse.name(), actualPage.getContent().get(0).name());
        verify(readUserUseCase).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve encontrar um usuário por ID e retornar a resposta DTO")
    void deve_encontrarUsuarioPorId_quandoSolicitado() {
        // Cenário: Mocka o use case para retornar a entidade esperada.
        when(readUserUseCase.findById(anyLong())).thenReturn(mockEntity);

        // Ação: Chama o método do controller com o ID.
        UserResponse actualResponse = userController.findById(1L);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        verify(readUserUseCase).findById(1L);
    }

    @Test
    @DisplayName("findByIdEntity - Deve encontrar um usuário por ID e retornar a entidade")
    void deve_encontrarUsuarioEntityPorId_quandoSolicitado() {
        // Cenário: Mocka o use case para retornar a entidade esperada.
        when(readUserUseCase.findById(anyLong())).thenReturn(mockEntity);

        // Ação: Chama o método do controller.
        UsersEntity actualEntity = userController.findByIdEntity(1L);

        // Verificação:
        assertNotNull(actualEntity);
        assertEquals(mockEntity.getId(), actualEntity.getId());
        verify(readUserUseCase).findById(1L);
    }

    @Test
    @DisplayName("findByLoginAndPassword - Deve retornar um token de login quando credenciais válidas são fornecidas")
    void deve_retornarToken_quandoLoginEsenhaValidos() {
        // Cenário: Mocka o use case para retornar um token de login.
        when(readUserUseCase.findByLoginAndPassword(any(LoginRequest.class))).thenReturn(mockEntity);

        // Ação: Chama o método do controller.
        LoginResponse actualResponse = userController.findByLoginAndPassword(mockLoginRequest);

        // Verificação:
        assertNotNull(actualResponse);
        verify(readUserUseCase).findByLoginAndPassword(mockLoginRequest);
    }

    @Test
    @DisplayName("updateUser - Deve atualizar um usuário e retornar a resposta DTO")
    void deve_atualizarUsuario_quandoSolicitado() {
        // Cenário: Mocka o use case para retornar a resposta DTO atualizada.
        when(updateUserUseCase.updateUser(anyLong(), any(CreateUserRequest.class))).thenReturn(mockResponse);

        // Ação: Chama o método do controller.
        UserResponse actualResponse = userController.updateUser(1L, mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        verify(updateUserUseCase).updateUser(1L, mockRequest);
    }

    @Test
    @DisplayName("deleteUser - Deve deletar um usuário com sucesso")
    void deve_deletarUsuario_quandoSolicitado() {
        // Cenário: Mocka o use case para não fazer nada quando chamado.
        doNothing().when(deleteUserUseCase).deleteUser(anyLong());

        // Ação: Chama o método do controller.
        userController.deleteUser(1L);

        // Verificação: Garante que o método do use case foi chamado uma vez.
        verify(deleteUserUseCase).deleteUser(1L);
    }
}