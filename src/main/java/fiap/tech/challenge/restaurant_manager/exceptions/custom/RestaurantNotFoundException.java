package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class RestaurantNotFoundException extends RuntimeException {
    private final Long restaurantId;

    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurant with id " + restaurantId + " not found.");
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
}
