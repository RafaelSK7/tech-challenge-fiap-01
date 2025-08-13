package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidCreateAddressRequest;
import static fiap.tech.challenge.restaurant_manager.utils.UserUtils.getValidUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateRestaurantUseCaseTest {
    @Mock
    private RestaurantsGateway restaurantsGateway;

    @Mock
    private UserController userController;

    @Mock
    private ValidateRestaurantService validateRestaurantService;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantsGateway, List.of(validateRestaurantService), userController);
    }

    @Test
    void testUpdateRestaurantSuccess() {
        Long restaurantId = 1L;
        RestaurantEntity existingRestaurant = new RestaurantEntity();
        existingRestaurant.setId(restaurantId);
        existingRestaurant.setName("Nome Antigo");
        existingRestaurant.setOwner(getValidUser());

        CreateRestaurantRequest request = new CreateRestaurantRequest(
                "Test Restaurant",
                getValidCreateAddressRequest(),
                CuisineType.ITALIAN,
                LocalTime.of(10, 0).toString(),
                LocalTime.of(22, 0).toString(),
                1L
        );

        // Simula a validação sem lançar exceção
        doNothing().when(validateRestaurantService).validate(any(CreateRestaurantRequest.class));

        when(restaurantsGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userController.findByIdEntity(request.ownerId())).thenReturn(getValidUser());
        when(restaurantsGateway.save(request, getValidUser())).thenReturn(existingRestaurant);

        RestaurantEntity restaurantEntity = updateRestaurantUseCase.updateRestaurant(restaurantId, request);

        assertNotNull(restaurantEntity);
        // O nome deve ter sido atualizado conforme o request
        assertEquals("Test Restaurant", restaurantEntity.getName());
    }

    @Test
    void testUpdateRestaurantNotFound() {
        Long restaurantId = 1L;
        CreateRestaurantRequest request = new CreateRestaurantRequest(
                "Test Restaurant",
                getValidCreateAddressRequest(),
                CuisineType.ITALIAN,
                LocalTime.of(10, 0).toString(),
                LocalTime.of(22, 0).toString(),
                1L
        );

        when(restaurantsGateway.findById(restaurantId)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                () -> updateRestaurantUseCase.updateRestaurant(restaurantId, request));
        assertEquals(any(), exception.getMessage());

    }


}
