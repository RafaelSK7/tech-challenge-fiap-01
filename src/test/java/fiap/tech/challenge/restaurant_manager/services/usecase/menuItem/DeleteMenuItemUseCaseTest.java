package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.usecases.menuItem.DeleteMenuItemUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseTest {

    private MenuItemRepository menuItemRepository;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(MenuItemRepository.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemRepository);
    }

    @Test
    void shouldDeleteMenuItemWhenFound() {
        Long id = 1L;
        MenuItem menuItem = new MenuItem();
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));
        deleteMenuItemUseCase.deleteMenuItem(id);
        verify(menuItemRepository, times(1)).findById(id);
        verify(menuItemRepository, times(1)).delete(menuItem);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {

        Long id = 999L;
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class,
                () -> deleteMenuItemUseCase.deleteMenuItem(id));
        assertEquals(id, exception.getMessage());
        verify(menuItemRepository, times(1)).findById(id);
        verify(menuItemRepository, never()).delete(any());
    }
}
