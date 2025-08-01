package fiap.tech.challenge.restaurant_manager.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadRestaurantUseCase {

    private RestaurantRepository restaurantRepository;

    public ReadRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Page<RestaurantResponse> findAll(Pageable pageable) {
        log.info("Entrou no use case de busca de todos os restaurantes.");
        log.info("Buscando restaurantes.");
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable);
        log.info("Montando DTO da lista de restaurantes.");
        List<RestaurantResponse> responseList = restaurantPage.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Retornando a lista.");
        return new PageImpl<>(responseList, pageable, restaurantPage.getTotalElements());
    }

    public RestaurantResponse findById(Long id) {
        log.info("Entrou no use case de busca de restaurante.");
        log.info("Buscando restaurante informado.");
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        log.info("Restaurante localizado.");
        return toResponse(restaurant);
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
        AddressResponse addressResponse = null;
        log.info("Montando o DTO de retorno do restaurante");
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
