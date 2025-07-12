package fiap.tech.challenge.restaurant_manager.entites.request;

import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;

public record CreateRestaurantRequest(
        String name,
        CreateAddressRequest address,
        CuisineType cuisineType,
        String startTime,
        String endTime,
        Long ownerId
) {
}
