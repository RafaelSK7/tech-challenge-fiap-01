package fiap.tech.challenge.restaurant_manager.application.presenters.address;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressPresenter {

    public static AddressResponse toResponse(AddressEntity entity){

        return new AddressResponse(entity.getStreet()
                , entity.getNumber()
                , entity.getNeighborhood()
                , entity.getCity()
                , entity.getState()
                , entity.getZipCode()
                , entity.getCountry() );
    }
}
