package fiap.tech.challenge.restaurant_manager.entites.request;

import fiap.tech.challenge.restaurant_manager.entites.enums.UserType;

public record CreateUserRequest(
        // TODO validar os dados de entrada como, notnull, not empty, email valido
        String name,
        String email,
        String login,
        String password,
        String address,
        UserType userType
) {
}
