package fiap.tech.challenge.restaurant_manager.application.interfaces.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantsInterface {

    RestaurantEntity save(CreateRestaurantRequest restaurantRequest, UsersEntity owner);

    Page<RestaurantEntity> findAll(Pageable page);

    Optional<RestaurantEntity> findById(Long id);

    void delete(RestaurantEntity restaurant);

    RestaurantEntity update(RestaurantEntity restaurant);
}
