package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UpdateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserController userService;
    private List<ValidateRestaurantService> updateRestaurantValidations;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository,
                                   List<ValidateRestaurantService> updateRestaurantValidations,
                                   UserController userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.updateRestaurantValidations = updateRestaurantValidations;
    }

    public RestaurantResponse updateRestaurant(Long id, CreateRestaurantRequest request) {

        log.info("Entrou no use case de atualizacao do restaurante.");
        log.info("Buscando restaurante a ser atualizado.");
        RestaurantEntity restaurantToUpdate = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        this.updateRestaurantValidations.forEach(v -> v.validate(request));

        log.info("Populando os campos do restaurante.");
        restaurantToUpdate.setName(request.name());
        restaurantToUpdate.setCuisineType(request.cuisineType().name());
        restaurantToUpdate.setStartTime(request.startTime());
        restaurantToUpdate.setEndTime(request.endTime());
        restaurantToUpdate.setAddress(new AddressEntity(request.address()));
        restaurantToUpdate.setLastUpdate(LocalDateTime.now());

        log.info("Buscando o owner do restaurante.");
        restaurantToUpdate.setOwner(userService.findByIdEntity(request.ownerId()));
        log.info("Restaurante populado.");
        return toResponse(restaurantRepository.save(restaurantToUpdate));
    }

    private RestaurantResponse toResponse(RestaurantEntity restaurant) {

        log.info("Montando DTO de retorno do restaurante.");
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
