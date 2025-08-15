package fiap.tech.challenge.restaurant_manager.application.gateway.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.UserTypeRepository;
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
class UserTypesGatewayTest {

    @InjectMocks
    private UserTypesGateway userTypesGateway;

    @Mock
    private UserTypeRepository userTypeRepository;

    private UserTypesEntity mockEntity;
    private CreateUserTypeRequest mockDto;

    @BeforeEach
    void setUp() {
        mockEntity = new UserTypesEntity();
        mockEntity.setUserTypeId(1L);
        mockEntity.setUserTypeName("CLIENT");

        mockDto = new CreateUserTypeRequest("CLIENT");
    }

    @Test
    @DisplayName("save - Deve salvar um tipo de usuário e retornar a entidade salva")
    void deve_salvarTipoUsuario_quandoChamado() {
        // Cenário: Mocka o repositório para retornar a entidade salva.
        when(userTypeRepository.save(any(UserTypesEntity.class))).thenReturn(mockEntity);

        // Ação: Chama o método do gateway.
        UserTypesEntity savedEntity = userTypesGateway.save(mockDto);

        // Verificação:
        assertNotNull(savedEntity);
        assertEquals(mockEntity.getUserTypeId(), savedEntity.getUserTypeId());
        verify(userTypeRepository).save(any(UserTypesEntity.class));
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de tipos de usuários")
    void deve_retornarPaginaDeTiposUsuarios_quandoChamado() {
        // Cenário: Mocka o repositório para retornar uma página paginada.
        Page<UserTypesEntity> expectedPage = new PageImpl<>(Collections.singletonList(mockEntity));
        when(userTypeRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chama o método do gateway.
        Page<UserTypesEntity> actualPage = userTypesGateway.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockEntity.getUserTypeName(), actualPage.getContent().get(0).getUserTypeName());
        verify(userTypeRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findByUserTypeId - Deve retornar um tipo de usuário quando o ID existe")
    void deve_retornarTipoUsuario_quandoIdExiste() {
        // Cenário: Mocka o repositório para retornar um Optional com a entidade.
        when(userTypeRepository.findByUserTypeId(anyLong())).thenReturn(Optional.of(mockEntity));

        // Ação: Chama o método do gateway.
        Optional<UserTypesEntity> optionalEntity = userTypesGateway.findByUserTypeId(1L);

        // Verificação:
        assertTrue(optionalEntity.isPresent());
        assertEquals(mockEntity.getUserTypeId(), optionalEntity.get().getUserTypeId());
        verify(userTypeRepository).findByUserTypeId(1L);
    }

    @Test
    @DisplayName("findByUserTypeId - Deve retornar um Optional vazio quando o ID não existe")
    void deve_retornarOptionalVazio_quandoIdNaoExiste() {
        // Cenário: Mocka o repositório para retornar um Optional vazio.
        when(userTypeRepository.findByUserTypeId(anyLong())).thenReturn(Optional.empty());

        // Ação: Chama o método do gateway.
        Optional<UserTypesEntity> optionalEntity = userTypesGateway.findByUserTypeId(99L);

        // Verificação:
        assertTrue(optionalEntity.isEmpty());
        verify(userTypeRepository).findByUserTypeId(99L);
    }

    @Test
    @DisplayName("delete - Deve deletar o tipo de usuário com sucesso")
    void deve_deletarTipoUsuario_quandoChamado() {
        // Cenário: Mocka o repositório para não fazer nada.
        doNothing().when(userTypeRepository).delete(any(UserTypesEntity.class));

        // Ação: Chama o método do gateway.
        userTypesGateway.delete(mockEntity);

        // Verificação: Garante que o método do repositório foi chamado.
        verify(userTypeRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("findByUserTypeName - Deve retornar um tipo de usuário quando o nome existe")
    void deve_retornarTipoUsuario_quandoNomeExiste() {
        // Cenário: Mocka o repositório para retornar um Optional com a entidade.
        when(userTypeRepository.findByUserTypeName(anyString())).thenReturn(Optional.of(mockEntity));

        // Ação: Chama o método do gateway.
        Optional<UserTypesEntity> optionalEntity = userTypesGateway.findByUserTypeName("ADMIN");

        // Verificação:
        assertTrue(optionalEntity.isPresent());
        assertEquals(mockEntity.getUserTypeName(), optionalEntity.get().getUserTypeName());
        verify(userTypeRepository).findByUserTypeName("ADMIN");
    }

    @Test
    @DisplayName("update - Deve atualizar um tipo de usuário e retornar a entidade atualizada")
    void deve_atualizarTipoUsuario_quandoChamado() {
        // Cenário: Mocka o repositório para retornar a entidade atualizada.
        UserTypesEntity updatedEntity = new UserTypesEntity();
        updatedEntity.setUserTypeId(1L);
        updatedEntity.setUserTypeName("CLIENT");
        when(userTypeRepository.save(any(UserTypesEntity.class))).thenReturn(updatedEntity);

        // Ação: Chama o método do gateway.
        UserTypesEntity actualEntity = userTypesGateway.update(updatedEntity);

        // Verificação:
        assertNotNull(actualEntity);
        assertEquals(updatedEntity.getUserTypeId(), actualEntity.getUserTypeId());
        assertEquals(updatedEntity.getUserTypeName(), actualEntity.getUserTypeName());
        verify(userTypeRepository).save(updatedEntity);
    }
}