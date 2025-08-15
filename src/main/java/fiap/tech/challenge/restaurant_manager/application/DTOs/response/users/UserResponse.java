package fiap.tech.challenge.restaurant_manager.application.DTOs.response.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String login,
        Long userTypeId,
        AddressResponse address,
        List<RestaurantEntity> restaurants) {
}

