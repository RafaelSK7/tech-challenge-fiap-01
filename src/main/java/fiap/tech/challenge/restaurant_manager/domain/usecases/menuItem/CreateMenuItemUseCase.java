package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        log.info("Entrou no use case de criacao do cardapio");
        this.createMenuItemValidations.forEach(v -> v.validate(menuItemRequest));
        log.info("Obtém o restaurante.");
        var restaurant = restaurantRepository.findById(menuItemRequest.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(menuItemRequest.restaurantId()));

        MenuItemEntity newMenuItem = new MenuItemEntity(
                null,
                menuItemRequest.name(),
                menuItemRequest.description(),
                menuItemRequest.price(),
                menuItemRequest.localOnly(),
                menuItemRequest.photoPath(),
                restaurant);
        log.info("Criou o cardapio.");
        MenuItemEntity saved = menuItemRepository.save(newMenuItem);
        log.info("Cardapio salvo com sucesso.");
        return toResponse(saved);
    }

    private MenuItemResponse toResponse(MenuItemEntity menuItem) {
        log.info("monta o DTO de retorno.");
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