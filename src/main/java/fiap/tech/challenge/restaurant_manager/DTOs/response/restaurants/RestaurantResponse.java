package fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants;

import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;

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
