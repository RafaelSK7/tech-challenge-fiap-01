package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadMenuItemUseCaseTest {

    private MenuItemsGateway menuItemsGateway;
    private ReadMenuItemUseCase readMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsGateway = mock(MenuItemsGateway.class);
        readMenuItemUseCase = new ReadMenuItemUseCase(menuItemsGateway);
    }

    @Test
    void testFindAllReturnsPageOfMenuItemResponse() {
        Pageable pageable = PageRequest.of(0, 2);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(1L);

        MenuItemEntity item1 = new MenuItemEntity(1L, "Pizza", "diversos sabores", 39.90, false, "pizza.jpg", restaurant);
        MenuItemEntity item2 = new MenuItemEntity(2L, "Hamburger", "pao, carne e queijo", 25.00, true, "burger.jpg", restaurant);

        List<MenuItemEntity> menuItems = List.of(item1, item2);
        Page<MenuItemEntity> menuItemPage = new PageImpl<>(menuItems, pageable, menuItems.size());
        when(menuItemsGateway.findAll(pageable)).thenReturn(menuItemPage);

        Page<MenuItemResponse> result = readMenuItemUseCase.findAll(pageable);
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());

        MenuItemResponse firstResponse = result.getContent().get(0);
        assertEquals(1L, firstResponse.id());
        assertEquals("Pizza", firstResponse.name());
        assertEquals("diversos sabores", firstResponse.description());

        MenuItemResponse secondResponse = result.getContent().get(1);
        assertEquals(2L, secondResponse.id());
        assertEquals("Hamburger", secondResponse.name());
        assertEquals("pao, carne e queijo", secondResponse.description());
        verify(menuItemsGateway, times(1)).findAll(pageable);
    }

    @Test
    void testFindByIdReturnsMenuItemResponse() {
        Long id = 1L;
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(10L);

        MenuItemEntity menuItem = new MenuItemEntity(id, "Pizza", "diversos sabores", 39.90, false, "pizza.jpg", restaurant);
        when(menuItemsGateway.findById(id)).thenReturn(Optional.of(menuItem));

        MenuItemEntity menuItemEntity = readMenuItemUseCase.findById(id);

        assertNotNull(menuItemEntity);
        assertEquals(id, menuItemEntity.getId());
        assertEquals("Pizza", menuItemEntity.getName());
        assertEquals("diversos sabores", menuItemEntity.getDescription());
        assertEquals(39.90, menuItemEntity.getPrice(), 0.001);
        assertFalse(menuItemEntity.getLocalOnly());
        assertEquals("pizza.jpg", menuItemEntity.getPhotoPath());
        assertEquals(10L, menuItemEntity.getRestaurant().getId());
        verify(menuItemsGateway, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsExceptionWhenNotFound() {

        Long id = 999L;
        when(menuItemsGateway.findById(id)).thenThrow(new InvalidMenuItemException(any()));

        assertThrows(InvalidMenuItemException.class, () -> readMenuItemUseCase.findById(id));
        verify(menuItemsGateway, times(1)).findById(id);
    }
}
