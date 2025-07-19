package fiap.tech.challenge.restaurant_manager.entites.response;


public record LoginResponse(
        Long id,
        String name,
        String login,
        Long userTypeId
) {
}
