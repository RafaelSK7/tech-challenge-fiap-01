package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteRestaurantUseCase {

    private final RestaurantsGateway restaurantsGateway;

    public DeleteRestaurantUseCase(RestaurantsGateway restaurantsGateway) {
        this.restaurantsGateway = restaurantsGateway;
    }

    public void deleteRestaurant(Long id) {
        log.info("Entrou no use case de remocao do restaurante.");
        log.info("Buscando o restaurante para remocao.");
        RestaurantEntity restaurantToDelete = restaurantsGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        log.info("Restaurante obtido com sucesso.");
        restaurantsGateway.delete(restaurantToDelete);
        log.info("Restaurante removido com sucesso.");
    }

}
