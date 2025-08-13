package fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import fiap.tech.challenge.restaurant_manager.application.gateway.restaurants.RestaurantsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateRestaurantService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;
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
    private RestaurantsGateway restaurantsGateway;

    @Mock
    private UserController userController;

    @Mock
    private ValidateRestaurantService validateRestaurantService;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createRestaurantUseCase = new CreateRestaurantUseCase(restaurantsGateway, userController, List.of(validateRestaurantService));
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

        UsersEntity mockOwner = new UsersEntity();
        mockOwner.setId(ownerId);
        mockOwner.setUserType(new UserTypesEntity(1L, "OWNER", LocalDateTime.now()));

        when(userController.findByIdEntity(ownerId)).thenReturn(mockOwner);

        // Captura o restaurante salvo para validação
        ArgumentCaptor<RestaurantEntity> restaurantCaptor = ArgumentCaptor.forClass(RestaurantEntity.class);
        ArgumentCaptor<CreateRestaurantRequest> requestCaptor = ArgumentCaptor.forClass(CreateRestaurantRequest.class);

        RestaurantEntity savedRestaurant = new RestaurantEntity(request, mockOwner);
        savedRestaurant.setId(100L);
        when(restaurantsGateway.save(any(CreateRestaurantRequest.class), any(UsersEntity.class))).thenReturn(savedRestaurant);

        // Act
        RestaurantEntity restaurantEntity = createRestaurantUseCase.createRestaurant(request);

        // Assert
        verify(validateRestaurantService, times(1)).validate(request);
        verify(userController, times(1)).findByIdEntity(ownerId);
        verify(restaurantsGateway, times(1)).save(requestCaptor.capture(), any(UsersEntity.class));
        RestaurantEntity capturedRestaurant = restaurantCaptor.getValue();

        assertEquals(request.name(), capturedRestaurant.getName());
        assertEquals(100L, restaurantEntity.getId());
        assertEquals(request.name(), restaurantEntity.getName());
        assertEquals(ownerId, restaurantEntity.getOwner().getId());
    }


}