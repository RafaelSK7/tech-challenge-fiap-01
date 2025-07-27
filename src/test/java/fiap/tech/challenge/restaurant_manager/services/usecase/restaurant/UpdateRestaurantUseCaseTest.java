package fiap.tech.challenge.restaurant_manager.services.usecase.restaurant;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.services.UserService;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateRestaurantService;
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
import static fiap.tech.challenge.restaurant_manager.utils.UserUtils.getValidUserResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateRestaurantUseCaseTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserService userService;

    @Mock
    private ValidateRestaurantService validateRestaurantService;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantRepository, List.of(validateRestaurantService), userService);
    }

    @Test
    void testUpdateRestaurantSuccess() {
        Long restaurantId = 1L;
        Restaurant existingRestaurant = new Restaurant();
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

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userService.findByIdEntity(request.ownerId())).thenReturn(getValidUser());
        when(restaurantRepository.save(existingRestaurant)).thenReturn(existingRestaurant);

        RestaurantResponse response = updateRestaurantUseCase.updateRestaurant(restaurantId, request);

        assertNotNull(response);
        // O nome deve ter sido atualizado conforme o request
        assertEquals("Test Restaurant", response.name());
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

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                () -> updateRestaurantUseCase.updateRestaurant(restaurantId, request));
        assertEquals(restaurantId, exception.getRestaurantId());
    }


}
