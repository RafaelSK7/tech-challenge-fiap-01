package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantsGateway restaurantsGateway;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantsGateway);
    }

    @Test
    void testDeleteRestaurantSuccess() {
        Long restaurantId = 1L;
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);

        when(restaurantsGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        deleteRestaurantUseCase.deleteRestaurant(restaurantId);

        verify(restaurantsGateway, times(1)).delete(restaurant);
    }

    @Test
    void testDeleteRestaurantNotFound() {
        Long restaurantId = 1L;

        when(restaurantsGateway.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> deleteRestaurantUseCase.deleteRestaurant(restaurantId));
        verify(restaurantsGateway, never()).delete(any(RestaurantEntity.class));
    }

}
