package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.entites.UserType;

import java.time.LocalDateTime;


import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidAddress;

public class UserUtils {

    public static User getValidUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Nome do Usuário");
        user.setEmail("usuario@example.com");
        user.setUserType(new UserType(1L, "OWNER", LocalDateTime.now()));
        user.setLogin("usuario123");
        user.setAddress(getValidAddress());
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
                user.getUserType().getUserTypeId(),
                new AddressResponse("Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "Estado Exemplo", "12345-678", "País Exemplo"),
        null   );
    }

}
