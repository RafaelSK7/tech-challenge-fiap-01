package fiap.tech.challenge.restaurant_manager.application.controller.menuItems;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.menuItens.MenuItemController;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.CreateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.DeleteMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.ReadMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.UpdateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
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
public class MenuItemsControllerTest {

    @InjectMocks
    private MenuItemController menuItemController;

    @Mock
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Mock
    private ReadMenuItemUseCase readMenuItemUseCase;

    @Mock
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Mock
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    private MenuItemEntity mockEntity;
    private CreateMenuItemRequest mockRequest;
    private MenuItemResponse mockResponse;
    private RestaurantEntity mockRestaurant;

    @BeforeEach
    void setUp() {
        mockRestaurant = new RestaurantEntity();

        mockEntity = new MenuItemEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Item de Teste");
        mockEntity.setDescription("Descrição de Teste");
        mockEntity.setPrice(10.00);
        mockEntity.setLocalOnly(true);
        mockEntity.setPhotoPath("foto.jpg");
        mockEntity.setRestaurant(mockRestaurant);

        mockRequest = new CreateMenuItemRequest("Item de Teste", "Descrição de Teste", 10.00, true, "foto.jpg", 1L);
        mockResponse = new MenuItemResponse(1L, "Item de Teste", "Descrição de Teste", 10.00, true, "foto.jpg", 1L);
    }

    @Test
    @DisplayName("createMenuItem - Deve criar um item de menu e retornar a resposta DTO")
    void deve_criarMenuItem_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(createMenuItemUseCase.createMenuItem(any(CreateMenuItemRequest.class))).thenReturn(mockEntity);

        // Ação: Chamar o método do controller
        MenuItemResponse actualResponse = menuItemController.createMenuItem(mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        assertEquals(mockResponse.name(), actualResponse.name());
        verify(createMenuItemUseCase).createMenuItem(mockRequest);
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de itens de menu")
    void deve_encontrarTodosMenuItems_quandoSolicitado() {
        // Cenário: Mock do use case para retornar uma página paginada
        Page<MenuItemResponse> expectedPage = new PageImpl<>(Collections.singletonList(mockResponse));
        when(readMenuItemUseCase.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chamar o método do controller com um objeto Pageable
        Page<MenuItemResponse> actualPage = menuItemController.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockResponse.name(), actualPage.getContent().get(0).name());
        verify(readMenuItemUseCase).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve encontrar um item de menu por ID e retornar a resposta DTO")
    void deve_encontrarMenuItemPorId_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(readMenuItemUseCase.findById(anyLong())).thenReturn(mockEntity);

        // Ação: Chamar o método do controller com o ID
        MenuItemResponse actualResponse = menuItemController.findById(1L);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        verify(readMenuItemUseCase).findById(1L);
    }

    @Test
    @DisplayName("updateMenuItem - Deve atualizar um item de menu e retornar a resposta DTO")
    void deve_atualizarMenuItem_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade atualizada
        MenuItemEntity updatedEntity = new MenuItemEntity();
        updatedEntity.setId(1L);
        updatedEntity.setName("Item Atualizado");
        updatedEntity.setDescription("Descrição Atualizada");
        updatedEntity.setPrice(20.00);
        updatedEntity.setLocalOnly(false);
        updatedEntity.setPhotoPath("feijoada.jpg");
        updatedEntity.setRestaurant(mockRestaurant);
        when(updateMenuItemUseCase.updateMenuItem(anyLong(), any(CreateMenuItemRequest.class))).thenReturn(updatedEntity);

        // Ação: Chamar o método do controller
        MenuItemResponse actualResponse = menuItemController.updateMenuItem(1L, mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(updatedEntity.getId(), actualResponse.id());
        assertEquals(updatedEntity.getName(), actualResponse.name());
        verify(updateMenuItemUseCase).updateMenuItem(1L, mockRequest);
    }

    @Test
    @DisplayName("deleteMenuItem - Deve deletar um item de menu com sucesso")
    void deve_deletarMenuItem_quandoSolicitado() {
        // Cenário: Mock do use case para não fazer nada
        doNothing().when(deleteMenuItemUseCase).deleteMenuItem(anyLong());

        // Ação: Chamar o método do controller
        menuItemController.deleteMenuItem(1L);

        // Verificação: Garantir que o método do use case foi chamado
        verify(deleteMenuItemUseCase).deleteMenuItem(1L);
    }
}