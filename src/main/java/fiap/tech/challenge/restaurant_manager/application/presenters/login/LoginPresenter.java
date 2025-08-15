package fiap.tech.challenge.restaurant_manager.application.presenters.login;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;

public class LoginPresenter {

    public static LoginResponse toResponse(UsersEntity entity){
        return new LoginResponse(entity.getId(), entity.getName(), entity.getLogin(), entity.getUserType().getUserTypeId());
    }
}
