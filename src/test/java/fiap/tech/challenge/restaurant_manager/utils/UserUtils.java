package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.enums.UserType;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;

import java.util.List;

public class UserUtils {

    public static User getValidUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Nome do Usuário");
        user.setEmail("usuario@example.com");
        user.setUserType(UserType.CLIENT);
        user.setLogin("usuario123");
        // adicione outros atributos necessários se houver
        return user;
    }

    public static UserResponse getValidUserResponse() {
        User user = getValidUser();
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getUserType().name(),
                new AddressResponse("Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "Estado Exemplo", "12345-678", "País Exemplo"),
        null   );
    }

}
