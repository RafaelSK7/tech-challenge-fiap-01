package fiap.tech.challenge.restaurant_manager.entites.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        String login,
        String userType,
        AddressResponse address) {
}

