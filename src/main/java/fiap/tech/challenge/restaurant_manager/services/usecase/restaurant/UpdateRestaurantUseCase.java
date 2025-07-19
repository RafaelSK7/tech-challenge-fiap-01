package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Address;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.UserService;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateRestaurantService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private List<ValidateRestaurantService> updateRestaurantValidations;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository,
                                   List<ValidateRestaurantService> updateRestaurantValidations,
                                   UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.updateRestaurantValidations = updateRestaurantValidations;
    }

    public RestaurantResponse updateRestaurant(Long id, CreateRestaurantRequest request) {

        Restaurant restaurantToUpdate = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        this.updateRestaurantValidations.forEach(v -> v.validate(request));

        restaurantToUpdate.setName(request.name());
        restaurantToUpdate.setCuisineType(request.cuisineType().name());
        restaurantToUpdate.setStartTime(request.startTime());
        restaurantToUpdate.setEndTime(request.endTime());
        restaurantToUpdate.setAddress(new Address(request.address()));
        restaurantToUpdate.setLastUpdate(LocalDateTime.now());

        restaurantToUpdate.setOwner(userService.findByIdEntity(request.ownerId()));

        return toResponse(restaurantRepository.save(restaurantToUpdate));
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
