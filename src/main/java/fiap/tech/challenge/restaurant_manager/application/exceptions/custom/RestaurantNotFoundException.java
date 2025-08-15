package fiap.tech.challenge.restaurant_manager.application.exceptions.custom;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurante com o ID " + restaurantId + " não encontrado.");
    }
}
