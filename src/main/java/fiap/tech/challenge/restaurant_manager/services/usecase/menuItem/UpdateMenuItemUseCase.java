package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateMenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final List<ValidateMenuItemService> updateMenuItemValidations;

    public UpdateMenuItemUseCase(MenuItemRepository menuItemRepository,
            RestaurantRepository restaurantRepository,
            List<ValidateMenuItemService> updateMenuItemValidations) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.updateMenuItemValidations = updateMenuItemValidations;
    }

    public MenuItemResponse updateMenuItem(Long id, CreateMenuItemRequest menuItemRequest) {
        MenuItem menuItemToUpdate = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));

        this.updateMenuItemValidations.forEach(v -> v.validate(menuItemRequest));

        Restaurant restaurant = restaurantRepository.findById(menuItemRequest.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(menuItemRequest.restaurantId()));

        menuItemToUpdate.setName(menuItemRequest.name());
        menuItemToUpdate.setDescription(menuItemRequest.description());
        menuItemToUpdate.setPrice(menuItemRequest.price());
        menuItemToUpdate.setLocalOnly(menuItemRequest.localOnly());
        menuItemToUpdate.setPhotoPath(menuItemRequest.photoPath());
        menuItemToUpdate.setRestaurant(restaurant);

        MenuItem updated = menuItemRepository.save(menuItemToUpdate);
        return toResponse(updated);
    }

    private MenuItemResponse toResponse(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isLocalOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId());
    }
}