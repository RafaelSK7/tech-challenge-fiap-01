package fiap.tech.challenge.restaurant_manager.entites.response;

import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;

public record RestaurantResponse(
        Long id,
        String name,
        AddressResponse address,
        String cuisineType,
        String startTime,
        String endTime,
        Long ownerId
) {
}
