package fiap.tech.challenge.restaurant_manager.entites.response;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String login,
        Long userTypeId,
        AddressResponse address,
        List<Restaurant> restaurants) {
}

