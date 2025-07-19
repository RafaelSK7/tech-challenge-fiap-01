package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.CreateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.DeleteRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.ReadRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.UpdateRestaurantUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private CreateRestaurantUseCase createRestaurantUseCase;
    private ReadRestaurantUseCase readRestaurantUseCase;
    private UpdateRestaurantUseCase updateRestaurantUseCase;
    private DeleteRestaurantUseCase deleteRestaurantUseCase;


    public RestaurantService(CreateRestaurantUseCase createRestaurantUseCase,
                             ReadRestaurantUseCase readRestaurantUseCase,
                             UpdateRestaurantUseCase updateRestaurantUseCase,
                             DeleteRestaurantUseCase deleteRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.readRestaurantUseCase = readRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
    }

    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        return createRestaurantUseCase.createRestaurant(request);
    }

    public Page<RestaurantResponse> findAll(Pageable page) {
        return readRestaurantUseCase.findAll(page);
    }

    public RestaurantResponse findById(Long id) {
        return readRestaurantUseCase.findById(id);
    }


    public RestaurantResponse updateRestaurant(Long id, CreateRestaurantRequest restaurantRequest) {
        return updateRestaurantUseCase.updateRestaurant(id, restaurantRequest);
    }

    public void deleteRestaurant(Long id) {
        deleteRestaurantUseCase.deleteRestaurant(id);
    }

}
