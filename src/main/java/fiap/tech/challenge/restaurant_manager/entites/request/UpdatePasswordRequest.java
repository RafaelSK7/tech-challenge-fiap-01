package fiap.tech.challenge.restaurant_manager.entites.request;

public record UpdatePasswordRequest(
        String login,
        String oldPassword,
        String newPassword
) {
}
