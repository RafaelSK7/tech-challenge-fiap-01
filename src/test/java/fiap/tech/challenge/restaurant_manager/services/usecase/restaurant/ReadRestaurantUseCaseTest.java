package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.usecases.restaurant.ReadRestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidAddress;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReadRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReadRestaurantUseCase readRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readRestaurantUseCase = new ReadRestaurantUseCase(restaurantRepository);
    }

    @Test
    void testReadRestaurantSuccess() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress(getValidAddress());

        User owner = new User();
        owner.setId(2L);
        restaurant.setOwner(owner);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        RestaurantResponse response = readRestaurantUseCase.findById(restaurantId);

        assertNotNull(response);
        assertEquals(restaurantId, response.id());
        assertEquals("Test Restaurant", response.name());
        assertEquals(owner.getId(), response.ownerId());
    }

    @Test
    void testReadRestaurantNotFound() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                () -> readRestaurantUseCase.findById(restaurantId));
    }

    @Test
    void testFindAllRestaurantsSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        User owner = new User();
        owner.setId(2L);
        restaurant.setOwner(owner);

        List<Restaurant> restaurants = List.of(restaurant);
        Page<Restaurant> restaurantPage = new PageImpl<>(restaurants, pageable, restaurants.size());

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);

        Page<RestaurantResponse> responsePage = readRestaurantUseCase.findAll(pageable);

        assertNotNull(responsePage);
        assertFalse(responsePage.isEmpty());
        assertEquals(1, responsePage.getContent().size());
        RestaurantResponse restaurantResponse = responsePage.getContent().get(0);
        assertEquals(restaurant.getId(), restaurantResponse.id());
        assertEquals(restaurant.getName(), restaurantResponse.name());
        assertEquals(owner.getId(), restaurantResponse.ownerId());
    }

}
