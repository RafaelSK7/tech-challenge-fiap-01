package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseTest {

    private MenuItemsGateway menuItemsGateway;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsGateway = mock(MenuItemsGateway.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemsGateway);
    }

    @Test
    void shouldDeleteMenuItemWhenFound() {
        Long id = 1L;
        MenuItemEntity menuItem = new MenuItemEntity();
        when(menuItemsGateway.findById(id)).thenReturn(Optional.of(menuItem));
        deleteMenuItemUseCase.deleteMenuItem(id);
        verify(menuItemsGateway, times(1)).findById(id);
        verify(menuItemsGateway, times(1)).delete(menuItem);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {

        Long id = 999L;
        when(menuItemsGateway.findById(id)).thenThrow(new InvalidMenuItemException(any()));
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class,
                () -> deleteMenuItemUseCase.deleteMenuItem(id));
        assertNotNull(exception.getMessage());
        verify(menuItemsGateway, times(1)).findById(id);
        verify(menuItemsGateway, never()).delete(any());
    }
}
