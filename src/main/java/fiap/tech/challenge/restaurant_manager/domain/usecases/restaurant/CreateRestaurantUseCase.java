package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CreateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserController userService;
    private final List<ValidateRestaurantService> createRestaurantValidations;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository,
                                   UserController userService,
                                   List<ValidateRestaurantService> createRestaurantValidations) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.createRestaurantValidations = createRestaurantValidations;
    }

    public RestaurantResponse createRestaurant(CreateRestaurantRequest restaurantRequest) {
        log.info("Entrou no use case de criacao do cardapio");
        this.createRestaurantValidations.forEach(v -> v.validate(restaurantRequest));
        log.info("Obtem o dono do restaurante.");
        UsersEntity owner = userService.findByIdEntity(restaurantRequest.ownerId());

        RestaurantEntity newRestaurant = new RestaurantEntity(restaurantRequest, owner);
        newRestaurant.setLastUpdate(LocalDateTime.now());

        log.info("Cria o restaurante.");
        RestaurantEntity saved = restaurantRepository.save(newRestaurant);
        log.info("Restaurante criade com sucesso.");
        return toResponse(saved);
    }

    private RestaurantResponse toResponse(RestaurantEntity restaurant) {

        log.info("Montando o DTO de retorno.");
        AddressResponse addressResponse = null;

        if (restaurant.getAddress() != null) {
            addressResponse = new AddressResponse(
                    restaurant.getAddress().getStreet(),
                    restaurant.getAddress().getNumber(),
                    restaurant.getAddress().getNeighborhood(),
                    restaurant.getAddress().getCity(),
                    restaurant.getAddress().getState(),
                    restaurant.getAddress().getZipCode(),
                    restaurant.getAddress().getCountry()
            );
        }

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                addressResponse,
                restaurant.getCuisineType(),
                restaurant.getStartTime(),
                restaurant.getEndTime(),
                restaurant.getOwner().getId()
        );
    }

}
