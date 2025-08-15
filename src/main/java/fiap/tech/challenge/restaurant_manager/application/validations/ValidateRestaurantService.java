package fiap.tech.challenge.restaurant_manager.application.validations;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;

public interface ValidateRestaurantService {

    void validate(CreateRestaurantRequest request);


}
