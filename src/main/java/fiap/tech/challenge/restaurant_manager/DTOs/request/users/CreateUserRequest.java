package fiap.tech.challenge.restaurant_manager.DTOs.request.users;


import fiap.tech.challenge.restaurant_manager.DTOs.request.address.CreateAddressRequest;

public record CreateUserRequest(
        String name,
        String email,
        String login,
        String password,
        CreateAddressRequest address,
        Long userTypeId
) {
}
