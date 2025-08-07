package fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;

public record CreateRestaurantRequest(
        String name,
        CreateAddressRequest address,
        CuisineType cuisineType,
        String startTime,
        String endTime,
        Long ownerId
) {
}
