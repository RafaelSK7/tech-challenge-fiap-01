package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public DeleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void deleteMenuItem(Long id) {
        MenuItem menuItemToDelete = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        menuItemRepository.delete(menuItemToDelete);
    }
}
