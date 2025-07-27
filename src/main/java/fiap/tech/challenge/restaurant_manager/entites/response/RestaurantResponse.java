package fiap.tech.challenge.restaurant_manager.entites.response;

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
