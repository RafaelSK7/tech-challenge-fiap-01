package fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants;

import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.address.CreateAddressRequest;

public record CreateRestaurantRequest(
        String name,
        CreateAddressRequest address,
        CuisineType cuisineType,
        String startTime,
        String endTime,
        Long ownerId
) {
}
