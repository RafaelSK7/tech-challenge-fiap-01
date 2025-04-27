package fiap.tech.challenge.restaurant_manager.entites.response;

import fiap.tech.challenge.restaurant_manager.entites.enums.UserType;

public record LoginResponse(
        Long id,
        String name,
        UserType userType
) {
}
