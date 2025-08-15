package fiap.tech.challenge.restaurant_manager.application.DTOs.response.login;


public record LoginResponse(
        Long id,
        String name,
        String login,
        Long userTypeId
) {
}
