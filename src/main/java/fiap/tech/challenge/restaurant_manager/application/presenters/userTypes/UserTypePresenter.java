package fiap.tech.challenge.restaurant_manager.application.presenters.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTypePresenter {

    public static UserTypeResponse toResponse(UserTypesEntity entity) {
        log.info("monta o DTO de retorno.");
        return new UserTypeResponse(entity.getUserTypeId(), entity.getUserTypeName());
    }
}
