package fiap.tech.challenge.restaurant_manager.application.gateway.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersGatewayTest {

    @InjectMocks
    private UsersGateway usersGateway;

    @Mock
    private UserRepository userRepository;

    private UsersEntity mockUserEntity;
    private UserTypesEntity mockUserTypeEntity;
    private CreateUserRequest mockCreateUserRequest;

    private AddressEntity mockAddressEntity;

    private CreateAddressRequest mockCreateAddressRequest;

    @BeforeEach
    void setUp() {
        mockUserTypeEntity = new UserTypesEntity();
        mockUserTypeEntity.setUserTypeId(1L);
        mockUserTypeEntity.setUserTypeName("CLIENT");

        mockAddressEntity = new AddressEntity();
        mockAddressEntity.setId(1L);
        mockAddressEntity.setStreet("Rua A");
        mockAddressEntity.setNumber("2 B");
        mockAddressEntity.setNeighborhood("São Miguel");
        mockAddressEntity.setCity("Sao Paulo");
        mockAddressEntity.setState("SP");
        mockAddressEntity.setZipCode("09876543");
        mockAddressEntity.setCountry("Brasil");

        mockUserEntity = new UsersEntity();
        mockUserEntity.setId(1L);
        mockUserEntity.setName("João");
        mockUserEntity.setEmail("joao@teste.com");
        mockUserEntity.setPassword("senha123");
        mockUserEntity.setUserType(mockUserTypeEntity);

        mockAddressEntity.setUser(mockUserEntity);

        mockUserEntity.setAddress(mockAddressEntity);

        mockCreateAddressRequest = new CreateAddressRequest("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil");
        mockCreateUserRequest = new CreateUserRequest("João", "joao@teste.com", "senha123", "12345678901", mockCreateAddressRequest, 1L);
    }

    @Test
    @DisplayName("save - Deve salvar um usuário e retornar a entidade salva")
    void deve_salvarUsuario_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade salva.
        when(userRepository.save(any(UsersEntity.class))).thenReturn(mockUserEntity);

        // Ação: Chama o método do gateway.
        UsersEntity savedUser = usersGateway.save(mockCreateUserRequest, mockUserTypeEntity);

        // Verificação:
        assertNotNull(savedUser);
        assertEquals(mockUserEntity.getId(), savedUser.getId());
        verify(userRepository).save(any(UsersEntity.class));
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de usuários")
    void deve_retornarPaginaDeUsuarios_quandoChamado() {
        // Cenário: Mock do repositório para retornar uma página paginada.
        Page<UsersEntity> expectedPage = new PageImpl<>(Collections.singletonList(mockUserEntity));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chama o método do gateway.
        Page<UsersEntity> actualPage = usersGateway.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockUserEntity.getName(), actualPage.getContent().get(0).getName());
        verify(userRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve retornar um usuário quando o ID existe")
    void deve_retornarUsuario_quandoIdExiste() {
        // Cenário: Mock do repositório para retornar um Optional com a entidade.
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserEntity));

        // Ação: Chama o método do gateway.
        Optional<UsersEntity> optionalEntity = usersGateway.findById(1L);

        // Verificação:
        assertTrue(optionalEntity.isPresent());
        assertEquals(mockUserEntity.getId(), optionalEntity.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("findById - Deve retornar um Optional vazio quando o ID não existe")
    void deve_retornarOptionalVazio_quandoIdNaoExiste() {
        // Cenário: Mock do repositório para retornar um Optional vazio.
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Ação: Chama o método do gateway.
        Optional<UsersEntity> optionalEntity = usersGateway.findById(99L);

        // Verificação:
        assertTrue(optionalEntity.isEmpty());
        verify(userRepository).findById(99L);
    }

    @Test
    @DisplayName("delete - Deve deletar o usuário com sucesso")
    void deve_deletarUsuario_quandoChamado() {
        // Cenário: Mock do repositório para não fazer nada.
        doNothing().when(userRepository).delete(any(UsersEntity.class));

        // Ação: Chama o método do gateway.
        usersGateway.delete(mockUserEntity);

        // Verificação: Garante que o método do repositório foi chamado.
        verify(userRepository).delete(mockUserEntity);
    }

    @Test
    @DisplayName("findByLoginAndPassword - Deve retornar um usuário quando o login e senha são válidos")
    void deve_retornarUsuario_quandoLoginEsenhaValidos() {
        // Cenário: Mock do repositório para retornar um Optional com a entidade.
        when(userRepository.findByLoginAndPassword(anyString(), anyString())).thenReturn(Optional.of(mockUserEntity));

        // Ação: Chama o método do gateway.
        Optional<UsersEntity> optionalUser = usersGateway.findByLoginAndPassword("joao@teste.com", "senha123");

        // Verificação:
        assertTrue(optionalUser.isPresent());
        assertEquals(mockUserEntity.getEmail(), optionalUser.get().getEmail());
        verify(userRepository).findByLoginAndPassword("joao@teste.com", "senha123");
    }

    @Test
    @DisplayName("findByLoginAndPassword - Deve retornar um Optional vazio quando o login e senha são inválidos")
    void deve_retornarOptionalVazio_quandoLoginEsenhaInvalidos() {
        // Cenário: Mock do repositório para retornar um Optional vazio.
        when(userRepository.findByLoginAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        // Ação: Chama o método do gateway.
        Optional<UsersEntity> optionalUser = usersGateway.findByLoginAndPassword("invalido@teste.com", "senhaerrada");

        // Verificação:
        assertTrue(optionalUser.isEmpty());
        verify(userRepository).findByLoginAndPassword("invalido@teste.com", "senhaerrada");
    }

    @Test
    @DisplayName("update - Deve atualizar um usuário e retornar a entidade atualizada")
    void deve_atualizarUsuario_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade atualizada.
        UsersEntity userToUpdate = new UsersEntity();
        userToUpdate.setId(1L);
        userToUpdate.setName("João Atualizado");
        userToUpdate.setEmail("joao_atualizado@teste.com");
        when(userRepository.save(any(UsersEntity.class))).thenReturn(userToUpdate);

        // Ação: Chama o método do gateway.
        UsersEntity actualUser = usersGateway.update(userToUpdate);

        // Verificação:
        assertNotNull(actualUser);
        assertEquals(userToUpdate.getId(), actualUser.getId());
        assertEquals("João Atualizado", actualUser.getName());
        verify(userRepository).save(userToUpdate);
    }
}