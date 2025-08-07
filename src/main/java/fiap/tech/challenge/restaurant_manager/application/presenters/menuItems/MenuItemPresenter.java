package fiap.tech.challenge.restaurant_manager.application.presenters.menuItems;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;

public class MenuItemPresenter {

    public static MenuItemResponse toResponse(MenuItemEntity entity) {
        return new MenuItemResponse(entity.getId()
                , entity.getName()
                , entity.getDescription()
                , entity.getPrice()
                , entity.getLocalOnly()
                , entity.getPhotoPath()
                , entity.getRestaurant().getId());
    }
}
