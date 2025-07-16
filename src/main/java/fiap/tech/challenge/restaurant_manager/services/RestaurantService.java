package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.CreateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.restaurant.ReadRestaurantUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private CreateRestaurantUseCase createRestaurantUseCase;
    private ReadRestaurantUseCase readRestaurantUseCase;


    public RestaurantService(CreateRestaurantUseCase createRestaurantUseCase, ReadRestaurantUseCase readRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.readRestaurantUseCase = readRestaurantUseCase;

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



}
