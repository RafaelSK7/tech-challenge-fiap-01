package fiap.tech.challenge.restaurant_manager.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;

    public DeleteRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void deleteRestaurant(Long id) {
        log.info("Entrou no use case de remocao do restaurante.");
        log.info("Buscando o restaurante para remocao.");
        Restaurant restaurantToDelete = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        log.info("Restaurante obtido com sucesso.");
        restaurantRepository.delete(restaurantToDelete);
        log.info("Restaurante removido com sucesso.");
    }

}
