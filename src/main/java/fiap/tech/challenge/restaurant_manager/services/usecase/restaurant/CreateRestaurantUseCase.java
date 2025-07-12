package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateRestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final List<ValidateRestaurantService> createRestaurantValidations;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository,
                                   UserRepository userRepository,
                                   List<ValidateRestaurantService> createRestaurantValidations) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.createRestaurantValidations = createRestaurantValidations;
    }

    public RestaurantResponse createRestaurant(CreateRestaurantRequest restaurantRequest) {
        this.createRestaurantValidations.forEach(v -> v.validate(restaurantRequest));

        User owner = userRepository.findById(restaurantRequest.ownerId())
                .orElseThrow(() -> new UserNotFoundException(restaurantRequest.ownerId()));

        Restaurant newRestaurant = new Restaurant(restaurantRequest, owner);

        Restaurant saved = restaurantRepository.save(newRestaurant);
        return toResponse(saved);
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
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
