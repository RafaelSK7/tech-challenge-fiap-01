package fiap.tech.challenge.restaurant_manager.entites.response;


import fiap.tech.challenge.restaurant_manager.entites.UserType;

public record LoginResponse(
        Long id,
        String name,
        String login,
        Long userTypeId
) {
}
