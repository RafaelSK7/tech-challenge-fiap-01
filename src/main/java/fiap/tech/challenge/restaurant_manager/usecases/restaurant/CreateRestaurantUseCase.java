package fiap.tech.challenge.restaurant_manager.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidUserTypeForRestaurantsException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.users.UserService;
import fiap.tech.challenge.restaurant_manager.validations.ValidateRestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CreateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final List<ValidateRestaurantService> createRestaurantValidations;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository,
                                   UserService userService,
                                   List<ValidateRestaurantService> createRestaurantValidations) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.createRestaurantValidations = createRestaurantValidations;
    }

    public RestaurantResponse createRestaurant(CreateRestaurantRequest restaurantRequest) {
        log.info("Entrou no use case de criacao do cardapio");
        this.createRestaurantValidations.forEach(v -> v.validate(restaurantRequest));
        log.info("Obtem o dono do restaurante.");
        User owner = userService.findByIdEntity(restaurantRequest.ownerId());

        log.info("Valida o UserType");
        if (owner.getUserType() == null || !owner.getUserType().getUserTypeName().equals("OWNER"))
            throw new InvalidUserTypeForRestaurantsException();

        Restaurant newRestaurant = new Restaurant(restaurantRequest, owner);
        newRestaurant.setLastUpdate(LocalDateTime.now());

        log.info("Cria o restaurante.");
        Restaurant saved = restaurantRepository.save(newRestaurant);
        log.info("Restaurante criade com sucesso.");
        return toResponse(saved);
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {

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
