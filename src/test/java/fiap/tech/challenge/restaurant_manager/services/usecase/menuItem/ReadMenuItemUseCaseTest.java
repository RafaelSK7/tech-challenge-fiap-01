package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.usecases.menuItem.ReadMenuItemUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadMenuItemUseCaseTest {

    private MenuItemRepository menuItemRepository;
    private ReadMenuItemUseCase readMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(MenuItemRepository.class);
        readMenuItemUseCase = new ReadMenuItemUseCase(menuItemRepository);
    }

    @Test
    void testFindAllReturnsPageOfMenuItemResponse() {
        Pageable pageable = PageRequest.of(0, 2);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item1 = new MenuItem(1L, "Pizza", "diversos sabores", 39.90, false, "pizza.jpg", restaurant);
        MenuItem item2 = new MenuItem(2L, "Hamburger", "pao, carne e queijo", 25.00, true, "burger.jpg", restaurant);

        List<MenuItem> menuItems = List.of(item1, item2);
        Page<MenuItem> menuItemPage = new PageImpl<>(menuItems, pageable, menuItems.size());
        when(menuItemRepository.findAll(pageable)).thenReturn(menuItemPage);

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
        verify(menuItemRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindByIdReturnsMenuItemResponse() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);

        MenuItem menuItem = new MenuItem(id, "Pizza", "diversos sabores", 39.90, false, "pizza.jpg", restaurant);
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        MenuItemResponse response = readMenuItemUseCase.findById(id);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Pizza", response.name());
        assertEquals("diversos sabores", response.description());
        assertEquals(39.90, response.price(), 0.001);
        assertFalse(response.localOnly());
        assertEquals("pizza.jpg", response.photoPath());
        assertEquals(10L, response.restaurantId());
        verify(menuItemRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsExceptionWhenNotFound() {

        Long id = 999L;
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InvalidMenuItemException.class, () -> readMenuItemUseCase.findById(id));
        verify(menuItemRepository, times(1)).findById(id);
    }
}
