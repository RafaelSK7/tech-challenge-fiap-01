package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateMenuItemUseCase {

    private final MenuItemsGateway menuItemGateway;
    private final RestaurantController restaurantController;
    private final List<ValidateMenuItemService> createMenuItemValidations;

    public CreateMenuItemUseCase(MenuItemsGateway menuItemRepository,
                                 RestaurantController restaurantController,
            List<ValidateMenuItemService> createMenuItemValidations) {
        this.menuItemGateway = menuItemRepository;
        this.restaurantController = restaurantController;
        this.createMenuItemValidations = createMenuItemValidations;
    }

    public MenuItemEntity createMenuItem(CreateMenuItemRequest menuItemRequest) {
        log.info("Entrou no use case de criacao do cardapio");
        this.createMenuItemValidations.forEach(v -> v.validate(menuItemRequest));
        log.info("Obtém o restaurante.");
        RestaurantEntity restaurant = restaurantController.findByIdEntity(menuItemRequest.restaurantId());
        log.info("Criou o cardapio.");
        MenuItemEntity menuItemEntity = menuItemGateway.save(menuItemRequest, restaurant);
        log.info("Cardapio salvo com sucesso.");
        return menuItemEntity;
    }
}