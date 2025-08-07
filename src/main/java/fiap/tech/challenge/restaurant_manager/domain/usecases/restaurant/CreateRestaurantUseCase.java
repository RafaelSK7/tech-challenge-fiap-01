package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateRestaurantUseCase {

    private final RestaurantsGateway restaurantsGateway;
    private final UserController userController;
    private final List<ValidateRestaurantService> createRestaurantValidations;

    public CreateRestaurantUseCase(RestaurantsGateway restaurantsGateway,
                                   UserController userController,
                                   List<ValidateRestaurantService> createRestaurantValidations) {
        this.restaurantsGateway = restaurantsGateway;
        this.userController = userController;
        this.createRestaurantValidations = createRestaurantValidations;
    }

    public RestaurantEntity createRestaurant(CreateRestaurantRequest restaurantRequest) {
        log.info("Entrou no use case de criacao do cardapio");
        this.createRestaurantValidations.forEach(v -> v.validate(restaurantRequest));
        log.info("Obtem o dono do restaurante.");
        UsersEntity owner = userController.findByIdEntity(restaurantRequest.ownerId());
        log.info("Cria o restaurante.");
        RestaurantEntity restaurant = restaurantsGateway.save(restaurantRequest, owner);
        log.info("Restaurante criado com sucesso.");
        return restaurant;
    }
}
