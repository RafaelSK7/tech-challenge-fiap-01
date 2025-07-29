package fiap.tech.challenge.restaurant_manager.validations;

import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;

public interface ValidateRestaurantService {

    void validate(CreateRestaurantRequest request);


}
