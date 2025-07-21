package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final List<ValidateMenuItemService> createMenuItemValidations;

    public CreateMenuItemUseCase(MenuItemRepository menuItemRepository,
            RestaurantRepository restaurantRepository,
            List<ValidateMenuItemService> createMenuItemValidations) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.createMenuItemValidations = createMenuItemValidations;
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest menuItemRequest) {
        this.createMenuItemValidations.forEach(v -> v.validate(menuItemRequest));

        var restaurant = restaurantRepository.findById(menuItemRequest.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(menuItemRequest.restaurantId()));

        MenuItem newMenuItem = new MenuItem(
                null,
                menuItemRequest.name(),
                menuItemRequest.description(),
                menuItemRequest.price(),
                menuItemRequest.localOnly(),
                menuItemRequest.photoPath(),
                restaurant);

        MenuItem saved = menuItemRepository.save(newMenuItem);
        return toResponse(saved);
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