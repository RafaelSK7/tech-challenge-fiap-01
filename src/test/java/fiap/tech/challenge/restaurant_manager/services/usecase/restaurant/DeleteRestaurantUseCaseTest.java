package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.usecases.restaurant.DeleteRestaurantUseCase;

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
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantRepository);
    }

    @Test
    void testDeleteRestaurantSuccess() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        deleteRestaurantUseCase.deleteRestaurant(restaurantId);

        verify(restaurantRepository, times(1)).delete(restaurant);
    }

    @Test
    void testDeleteRestaurantNotFound() {
        Long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> deleteRestaurantUseCase.deleteRestaurant(restaurantId));
        verify(restaurantRepository, never()).delete(any(Restaurant.class));
    }

}
