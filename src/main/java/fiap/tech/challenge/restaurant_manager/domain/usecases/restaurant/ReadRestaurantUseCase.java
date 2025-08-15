package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
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

    private RestaurantsGateway restaurantsGateway;

    public ReadRestaurantUseCase(RestaurantsGateway restaurantsGateway) {
        this.restaurantsGateway = restaurantsGateway;
    }

    public Page<RestaurantResponse> findAll(Pageable pageable) {
        log.info("Entrou no use case de busca de todos os restaurantes.");
        log.info("Buscando restaurantes.");
        Page<RestaurantEntity> restaurantPage = restaurantsGateway.findAll(pageable);
        log.info("Montando DTO da lista de restaurantes.");
        List<RestaurantResponse> responseList = restaurantPage.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Retornando a lista.");
        return new PageImpl<>(responseList, pageable, restaurantPage.getTotalElements());
    }

    public RestaurantEntity findById(Long id) {
        log.info("Entrou no use case de busca de restaurante.");
        log.info("Buscando restaurante informado.");
        return restaurantsGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    private RestaurantResponse toResponse(RestaurantEntity restaurant) {
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
