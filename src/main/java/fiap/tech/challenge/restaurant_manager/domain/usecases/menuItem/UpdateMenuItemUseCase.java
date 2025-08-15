package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UpdateMenuItemUseCase {

    private final MenuItemsGateway menuItemsGateway;
    private final RestaurantController restaurantController;
    private final List<ValidateMenuItemService> updateMenuItemValidations;

    public UpdateMenuItemUseCase(MenuItemsGateway menuItemsGateway,
                                 RestaurantController restaurantController,
                                 List<ValidateMenuItemService> updateMenuItemValidations) {
        this.menuItemsGateway = menuItemsGateway;
        this.restaurantController = restaurantController;
        this.updateMenuItemValidations = updateMenuItemValidations;
    }

    public MenuItemEntity updateMenuItem(Long id, CreateMenuItemRequest menuItemRequest) {
        log.info("Entrou no use case de atualizacao do cardapio.");
        log.info("Buscando cardapio a ser atualizado.");
        MenuItemEntity menuItemToUpdate = menuItemsGateway.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));

        this.updateMenuItemValidations.forEach(v -> v.validate(menuItemRequest));

        log.info("Buscando restaurante ao qual pertence o cardapio.");
        RestaurantEntity restaurant = restaurantController.findByIdEntity(menuItemRequest.restaurantId());

        log.info("Populando os campos do cardapio.");
        menuItemToUpdate.setName(menuItemRequest.name());
        menuItemToUpdate.setDescription(menuItemRequest.description());
        menuItemToUpdate.setPrice(menuItemRequest.price());
        menuItemToUpdate.setLocalOnly(menuItemRequest.localOnly());
        menuItemToUpdate.setPhotoPath(menuItemRequest.photoPath());
        menuItemToUpdate.setRestaurant(restaurant);
        log.info("Cardapio populado.");
        MenuItemEntity updated = menuItemsGateway.update(menuItemToUpdate);
        log.info("Cardapio salvo com sucesso.");
        return updated;
    }
}