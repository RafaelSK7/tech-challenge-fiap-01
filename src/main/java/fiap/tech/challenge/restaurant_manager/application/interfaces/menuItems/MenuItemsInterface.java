package fiap.tech.challenge.restaurant_manager.application.interfaces.menuItems;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MenuItemsInterface {

    MenuItemEntity save(CreateMenuItemRequest menuItemRequest, RestaurantEntity restaurant);

    Page<MenuItemEntity> findAll(Pageable page);

    Optional<MenuItemEntity> findById(Long id);

    void delete(MenuItemEntity menuItem);

    MenuItemEntity update(MenuItemEntity menuItem);
}
