package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;

import java.time.LocalDateTime;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidAddress;

public class UserUtils {

    public static UsersEntity getValidUser() {
        UsersEntity user = new UsersEntity();
        user.setId(1L);
        user.setName("Nome do Usuário");
        user.setEmail("usuario@example.com");
        user.setUserType(new UserTypesEntity(1L, "CLIENT", LocalDateTime.now()));
        user.setLogin("usuario123");
        user.setAddress(getValidAddress());
        // adicione outros atributos necessários se houver
        return user;
    }

    public static UserResponse getValidUserResponse() {
        UsersEntity user = getValidUser();
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getUserType().getUserTypeId(),
                new AddressResponse("Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "Estado Exemplo", "12345-678", "País Exemplo"),
                null);
    }

}
