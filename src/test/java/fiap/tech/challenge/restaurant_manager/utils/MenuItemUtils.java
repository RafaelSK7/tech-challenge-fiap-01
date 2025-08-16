package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;

public class MenuItemUtils {

    public static MenuItemEntity getValidMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setId(1L);
        menuItem.setName("Hambúrguer");
        menuItem.setDescription("Delicioso hambúrguer artesanal");
        menuItem.setPrice(29.90);
        menuItem.setLocalOnly(true);
        menuItem.setPhotoPath("https://example.com/hamburguer.jpg");


        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(1L);
        menuItem.setRestaurant(restaurant);

        return menuItem;
    }

    public static CreateMenuItemRequest getValidCreateMenuItemRequest() {
        return new CreateMenuItemRequest(
                "Hambúrguer",
                "Delicioso hambúrguer artesanal",
                29.90,
                true,
                "https://example.com/hamburguer.jpg",
                1L
        );
    }

    public static MenuItemResponse getValidMenuItemResponse() {
        MenuItemEntity menuItem = getValidMenuItem();
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getLocalOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId()
        );
    }
}