package fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;

public record CreateRestaurantRequest(
        String name,
        CreateAddressRequest address,
        CuisineType cuisineType,
        String startTime,
        String endTime,
        Long ownerId
) {
}
