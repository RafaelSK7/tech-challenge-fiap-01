package fiap.tech.challenge.restaurant_manager.entites.request;


public record CreateUserRequest(
        String name,
        String email,
        String login,
        String password,
        CreateAddressRequest address,
        Long userTypeId
) {
}
