package fiap.tech.challenge.restaurant_manager.DTOs.response.users;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;

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

