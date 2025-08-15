package fiap.tech.challenge.restaurant_manager.application.gateway.menuItems;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.interfaces.menuItems.MenuItemsInterface;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MenuItemsGateway implements MenuItemsInterface {

    private MenuItemRepository menuItemRepository;

    public MenuItemsGateway(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItemEntity save(CreateMenuItemRequest menuItemRequest, RestaurantEntity restaurant) {
        MenuItemEntity newMenuItem = new MenuItemEntity(menuItemRequest, restaurant);
        return menuItemRepository.save(newMenuItem);
    }

    @Override
    public Page<MenuItemEntity> findAll(Pageable page) {
        return menuItemRepository.findAll(page);
    }

    @Override
    public Optional<MenuItemEntity> findById(Long id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public void delete(MenuItemEntity menuItem) {
        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItemEntity update(MenuItemEntity menuItem) {
        return menuItemRepository.save(menuItem);
    }
}
