package fiap.tech.challenge.restaurant_manager.application.presenters.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.presenters.address.AddressPresenter;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestaurantPresenter {

    public static RestaurantResponse toResponse(RestaurantEntity entity) {
        log.info("monta o DTO de retorno.");
        return new RestaurantResponse(entity.getId()
                , entity.getName()
                , AddressPresenter.toResponse(entity.getAddress())
                , entity.getCuisineType()
                , entity.getStartTime()
                , entity.getEndTime()
                , entity.getOwner().getId());
    }
}
