package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;

public class MenuItemUtils {

    public static MenuItem getValidMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Hambúrguer");
        menuItem.setDescription("Delicioso hambúrguer artesanal");
        menuItem.setPrice(29.90);
        menuItem.setLocalOnly(true);
        menuItem.setPhotoPath("https://example.com/hamburguer.jpg");

        
        Restaurant restaurant = new Restaurant();
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
        MenuItem menuItem = getValidMenuItem();
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isLocalOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId()
        );
    }
}