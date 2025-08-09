package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.users.UserService;
import fiap.tech.challenge.restaurant_manager.usecases.restaurant.CreateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidateRestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static fiap.tech.challenge.restaurant_manager.utils.AdressUtils.getValidCreateAddressRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserService userService;

    @Mock
    private ValidateRestaurantService validateRestaurantService;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createRestaurantUseCase = new CreateRestaurantUseCase(restaurantRepository, userService, List.of(validateRestaurantService));
    }

    @Test
    void testCreateRestaurantSuccess() {
        // Arrange
        Long ownerId = 1L;
        CreateRestaurantRequest request = new CreateRestaurantRequest(
                "Test Restaurant",
                getValidCreateAddressRequest(),
                CuisineType.ITALIAN,
                LocalTime.of(10, 0).toString(),
                LocalTime.of(22, 0).toString(),
                ownerId
        );

        User mockOwner = new User();
        mockOwner.setId(ownerId);
        mockOwner.setUserType(new UserType(1L, "OWNER", LocalDateTime.now()));

        when(userService.findByIdEntity(ownerId)).thenReturn(mockOwner);

        // Captura o restaurante salvo para validação
        ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);

        Restaurant savedRestaurant = new Restaurant(request, mockOwner);
        savedRestaurant.setId(100L);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(savedRestaurant);

        // Act
        RestaurantResponse response = createRestaurantUseCase.createRestaurant(request);

        // Assert
        verify(validateRestaurantService, times(1)).validate(request);
        verify(userService, times(1)).findByIdEntity(ownerId);
        verify(restaurantRepository, times(1)).save(restaurantCaptor.capture());
        Restaurant capturedRestaurant = restaurantCaptor.getValue();

        assertEquals(request.name(), capturedRestaurant.getName());
        assertEquals(100L, response.id());
        assertEquals(request.name(), response.name());
        assertEquals(ownerId, response.ownerId());
    }


}