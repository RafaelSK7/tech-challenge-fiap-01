package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidAddressResponse;
import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidCreateAddressRequest;

public class RestaurantUtils {

    public static CreateRestaurantRequest getValidCreateRestaurantRequest() {
        return new CreateRestaurantRequest(
                "Restaurante Exemplo",
                getValidCreateAddressRequest(),
                CuisineType.ITALIAN,
                "10",
                "22",
                1L
        );
    }

    public static RestaurantResponse getValidRestaurantResponse() {
        return new RestaurantResponse(
                1L,
                "Restaurante Exemplo",
                getValidAddressResponse(),
                CuisineType.ITALIAN.name(),
                "10",
                "22",
                1L
        );
    }

}
