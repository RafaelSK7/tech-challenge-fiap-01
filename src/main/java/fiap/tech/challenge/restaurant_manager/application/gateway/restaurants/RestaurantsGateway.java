package fiap.tech.challenge.restaurant_manager.application.gateway.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.interfaces.restaurants.RestaurantsInterface;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class RestaurantsGateway implements RestaurantsInterface {

    private RestaurantRepository restaurantRepository;

    public RestaurantsGateway(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantEntity save(CreateRestaurantRequest restaurantRequest, UsersEntity owner) {
        RestaurantEntity newRestaurant = new RestaurantEntity(restaurantRequest, owner);
        newRestaurant.setLastUpdate(LocalDateTime.now());
        log.info("Gravando o restaurante.");
        return restaurantRepository.save(newRestaurant);
    }

    @Override
    public Page<RestaurantEntity> findAll(Pageable page) {
        log.info("Buscando todos os restaurantes.");
        return restaurantRepository.findAll(page);
    }

    @Override
    public Optional<RestaurantEntity> findById(Long id) {
        log.info(String.format("Buscando o restaurante com ID %d.", id));
        return restaurantRepository.findById(id);
    }

    @Override
    public void delete(RestaurantEntity restaurant) {
        log.info("Deletando o restaurante.");
        restaurantRepository.delete(restaurant);
    }

    @Override
    public RestaurantEntity update(RestaurantEntity restaurant) {
        log.info("Atualizando o restaurante.");
        return restaurantRepository.save(restaurant);
    }
}
