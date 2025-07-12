package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurante com o ID " + restaurantId + " não encontrado.");
    }
}
