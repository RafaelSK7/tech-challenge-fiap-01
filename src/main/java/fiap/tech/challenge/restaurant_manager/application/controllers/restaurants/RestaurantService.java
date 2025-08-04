package fiap.tech.challenge.restaurant_manager.application.controllers.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.CreateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.DeleteRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.ReadRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.UpdateRestaurantUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
        log.info("Entrou no servico de cadastro do restaurante.");
        return createRestaurantUseCase.createRestaurant(request);
    }

    public Page<RestaurantResponse> findAll(Pageable page) {
        log.info("Entrou no servico de busca de todos os restaurantes.");
        return readRestaurantUseCase.findAll(page);
    }

    public RestaurantResponse findById(Long id) {
        log.info("Entrou no servico de busca do restaurante.");
        return readRestaurantUseCase.findById(id);
    }


    public RestaurantResponse updateRestaurant(Long id, CreateRestaurantRequest restaurantRequest) {
        log.info("Entrou no servico de atualizacao do restaurante.");
        return updateRestaurantUseCase.updateRestaurant(id, restaurantRequest);
    }

    public void deleteRestaurant(Long id) {
        log.info("Entrou no servico de remocao do restaurante.");
        deleteRestaurantUseCase.deleteRestaurant(id);
    }

}
