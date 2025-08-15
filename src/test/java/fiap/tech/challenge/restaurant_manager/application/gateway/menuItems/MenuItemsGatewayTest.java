package fiap.tech.challenge.restaurant_manager.application.gateway.menuItems;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.MenuItemRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemsGatewayTest {

    @InjectMocks
    private MenuItemsGateway menuItemsGateway;

    @Mock
    private MenuItemRepository menuItemRepository;

    private MenuItemEntity mockMenuItemEntity;
    private CreateMenuItemRequest mockCreateMenuItemRequest;
    private RestaurantEntity mockRestaurantEntity;

    @BeforeEach
    void setUp() {
        mockRestaurantEntity = new RestaurantEntity();
        mockRestaurantEntity.setId(1L);
        mockRestaurantEntity.setName("Restaurante Teste");

        mockMenuItemEntity = new MenuItemEntity();
        mockMenuItemEntity.setId(1L);
        mockMenuItemEntity.setName("Item de Teste");
        mockMenuItemEntity.setPrice(10.00);
        mockMenuItemEntity.setRestaurant(mockRestaurantEntity);

        mockCreateMenuItemRequest = new CreateMenuItemRequest("Item de Teste", "Descrição de Teste", 10.00, true, "foto.jpg", 1L);
    }

    @Test
    @DisplayName("save - Deve salvar um item de menu e retornar a entidade salva")
    void deve_salvarMenuItem_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade salva
        when(menuItemRepository.save(any(MenuItemEntity.class))).thenReturn(mockMenuItemEntity);

        // Ação: Chamar o método do gateway
        MenuItemEntity savedMenuItem = menuItemsGateway.save(mockCreateMenuItemRequest, mockRestaurantEntity);

        // Verificação:
        assertNotNull(savedMenuItem);
        assertEquals(mockMenuItemEntity.getId(), savedMenuItem.getId());
        verify(menuItemRepository).save(any(MenuItemEntity.class));
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de itens de menu")
    void deve_retornarPaginaDeMenuItems_quandoChamado() {
        // Cenário: Mock do repositório para retornar uma página paginada
        Page<MenuItemEntity> expectedPage = new PageImpl<>(Collections.singletonList(mockMenuItemEntity));
        when(menuItemRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chamar o método do gateway
        Page<MenuItemEntity> actualPage = menuItemsGateway.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockMenuItemEntity.getName(), actualPage.getContent().get(0).getName());
        verify(menuItemRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve retornar um item de menu quando o ID existe")
    void deve_retornarMenuItem_quandoIdExiste() {
        // Cenário: Mock do repositório para retornar um Optional com a entidade
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(mockMenuItemEntity));

        // Ação: Chamar o método do gateway
        Optional<MenuItemEntity> optionalEntity = menuItemsGateway.findById(1L);

        // Verificação:
        assertTrue(optionalEntity.isPresent());
        assertEquals(mockMenuItemEntity.getId(), optionalEntity.get().getId());
        verify(menuItemRepository).findById(1L);
    }

    @Test
    @DisplayName("findById - Deve retornar um Optional vazio quando o ID não existe")
    void deve_retornarOptionalVazio_quandoIdNaoExiste() {
        // Cenário: Mock do repositório para retornar um Optional vazio
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Ação: Chamar o método do gateway
        Optional<MenuItemEntity> optionalEntity = menuItemsGateway.findById(99L);

        // Verificação:
        assertTrue(optionalEntity.isEmpty());
        verify(menuItemRepository).findById(99L);
    }

    @Test
    @DisplayName("delete - Deve deletar o item de menu com sucesso")
    void deve_deletarMenuItem_quandoChamado() {
        // Cenário: Mock do repositório para não fazer nada
        doNothing().when(menuItemRepository).delete(any(MenuItemEntity.class));

        // Ação: Chamar o método do gateway
        menuItemsGateway.delete(mockMenuItemEntity);

        // Verificação: Garantir que o método do repositório foi chamado
        verify(menuItemRepository).delete(mockMenuItemEntity);
    }

    @Test
    @DisplayName("update - Deve atualizar um item de menu e retornar a entidade atualizada")
    void deve_atualizarMenuItem_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade atualizada
        MenuItemEntity updatedMenuItem = new MenuItemEntity();
        updatedMenuItem.setId(1L);
        updatedMenuItem.setName("Item Atualizado");
        updatedMenuItem.setPrice(15.00);
        when(menuItemRepository.save(any(MenuItemEntity.class))).thenReturn(updatedMenuItem);

        // Ação: Chamar o método do gateway
        MenuItemEntity actualMenuItem = menuItemsGateway.update(updatedMenuItem);

        // Verificação:
        assertNotNull(actualMenuItem);
        assertEquals(updatedMenuItem.getId(), actualMenuItem.getId());
        assertEquals(updatedMenuItem.getName(), actualMenuItem.getName());
        verify(menuItemRepository).save(updatedMenuItem);
    }
}