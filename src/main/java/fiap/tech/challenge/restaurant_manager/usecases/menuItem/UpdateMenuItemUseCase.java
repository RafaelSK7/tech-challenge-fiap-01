package fiap.tech.challenge.restaurant_manager.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.validations.ValidateMenuItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        log.info("Entrou no use case de atualizacao do cardapio.");
        log.info("Buscando cardapio a ser atualizado.");
        MenuItem menuItemToUpdate = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));

        this.updateMenuItemValidations.forEach(v -> v.validate(menuItemRequest));

        log.info("Buscando restaurante ao qual pertence o cardapio.");
        Restaurant restaurant = restaurantRepository.findById(menuItemRequest.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(menuItemRequest.restaurantId()));

        log.info("Populando os campos do cardapio.");
        menuItemToUpdate.setName(menuItemRequest.name());
        menuItemToUpdate.setDescription(menuItemRequest.description());
        menuItemToUpdate.setPrice(menuItemRequest.price());
        menuItemToUpdate.setLocalOnly(menuItemRequest.localOnly());
        menuItemToUpdate.setPhotoPath(menuItemRequest.photoPath());
        menuItemToUpdate.setRestaurant(restaurant);
        log.info("Cardapio populado.");
        MenuItem updated = menuItemRepository.save(menuItemToUpdate);
        log.info("Cardapio salvo com sucesso.");
        return toResponse(updated);
    }

    private MenuItemResponse toResponse(MenuItem menuItem) {
        log.info("Montando DTO de retorno do cardapio.");
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