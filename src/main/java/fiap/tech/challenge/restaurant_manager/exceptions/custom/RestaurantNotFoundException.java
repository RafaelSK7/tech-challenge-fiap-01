package fiap.tech.challenge.restaurant_manager.exceptions.custom;

import lombok.Getter;

@Getter
public class RestaurantNotFoundException extends RuntimeException {
    private final Long restaurantId;

    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurant with id " + restaurantId + " not found.");
        this.restaurantId = restaurantId;
    }

}
