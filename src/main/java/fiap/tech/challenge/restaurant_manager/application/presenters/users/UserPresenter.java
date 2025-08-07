package fiap.tech.challenge.restaurant_manager.application.presenters.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.presenters.address.AddressPresenter;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPresenter {
    public static UserResponse toResponse(UsersEntity entity) {
        log.info("monta o DTO de retorno.");
        return new UserResponse(entity.getId()
                , entity.getName()
                , entity.getEmail()
                , entity.getLogin()
                , entity.getUserType().getUserTypeId()
                , AddressPresenter.toResponse(entity.getAddress())
                , entity.getRestaurants());
    }
}
