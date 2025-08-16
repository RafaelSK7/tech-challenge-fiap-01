package fiap.tech.challenge.restaurant_manager.application.DTOs.request.users;


import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;

public record CreateUserRequest(
        String name,
        String email,
        String login,
        String password,
        CreateAddressRequest address,
        Long userTypeId
) {
}
