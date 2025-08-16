package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UpdateRestaurantUseCase {

    private final RestaurantsGateway restaurantsGateway;
    private final UserController userController;
    private List<ValidateRestaurantService> updateRestaurantValidations;

    public UpdateRestaurantUseCase(RestaurantsGateway restaurantsGateway,
                                   List<ValidateRestaurantService> updateRestaurantValidations,
                                   UserController userController) {
        this.restaurantsGateway = restaurantsGateway;
        this.userController = userController;
        this.updateRestaurantValidations = updateRestaurantValidations;
    }

    public RestaurantEntity updateRestaurant(Long id, CreateRestaurantRequest request) {

        log.info("Entrou no use case de atualizacao do restaurante.");

        this.updateRestaurantValidations.forEach(v -> v.validate(request));

        log.info("Buscando restaurante a ser atualizado.");
        RestaurantEntity restaurantToUpdate = restaurantsGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        log.info("Populando os campos do restaurante.");
        restaurantToUpdate.setName(request.name());
        restaurantToUpdate.setCuisineType(request.cuisineType().name());
        restaurantToUpdate.setStartTime(request.startTime());
        restaurantToUpdate.setEndTime(request.endTime());
        restaurantToUpdate.setAddress(new AddressEntity(request.address()));
        restaurantToUpdate.setLastUpdate(LocalDateTime.now());

        log.info("Buscando o owner do restaurante.");
        restaurantToUpdate.setOwner(userController.findByIdEntity(request.ownerId()));
        log.info("Restaurante populado.");

        return restaurantsGateway.update(restaurantToUpdate);
    }

}
