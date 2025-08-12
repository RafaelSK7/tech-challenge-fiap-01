package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.usecases.menuItem.CreateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidateMenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseTest {

    private MenuItemRepository menuItemRepository;
    private RestaurantRepository restaurantRepository;
    private ValidateMenuItemService validationService;
    private CreateMenuItemUseCase createMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(MenuItemRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        validationService = mock(ValidateMenuItemService.class);
        createMenuItemUseCase = new CreateMenuItemUseCase(
                menuItemRepository,
                restaurantRepository,
                List.of(validationService)
        );
    }

    @Test
    void testCreateMenuItemSuccess() {
        Long restaurantId = 1L;
        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Pizza",
                "Deliciosa pizza de calabresa",
                39.90,
                false,
                "pizza.jpg",
                restaurantId
        );

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        MenuItem savedMenuItem = new MenuItem(
                10L,
                request.name(),
                request.description(),
                request.price(),
                request.localOnly(),
                request.photoPath(),
                restaurant
        );

        doNothing().when(validationService).validate(request);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(savedMenuItem);

        MenuItemResponse response = createMenuItemUseCase.createMenuItem(request);

        verify(validationService).validate(request);
        verify(menuItemRepository).save(any(MenuItem.class));

        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals("Pizza", response.name());
        assertEquals("Deliciosa pizza de calabresa", response.description());
        assertEquals(39.90, response.price(), 0.001);
        assertFalse(response.localOnly());
        assertEquals("pizza.jpg", response.photoPath());
        assertEquals(restaurantId, response.restaurantId());
    }

    @Test
    void testCreateMenuItemRestaurantNotFound() {
        Long restaurantId = 999L;
        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Sushi",
                "Sushi fresco",
                49.90,
                true,
                "sushi.jpg",
                restaurantId
        );

        doNothing().when(validationService).validate(request);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> createMenuItemUseCase.createMenuItem(request));
        verify(menuItemRepository, never()).save(any());
    }

    @Test
    void testMultipleValidationsAreCalled() {
        ValidateMenuItemService validation1 = mock(ValidateMenuItemService.class);
        ValidateMenuItemService validation2 = mock(ValidateMenuItemService.class);

        CreateMenuItemUseCase useCaseWithMultipleValidations = new CreateMenuItemUseCase(
                menuItemRepository,
                restaurantRepository,
                List.of(validation1, validation2)
        );

        Long restaurantId = 1L;
        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Hamburguer",
                "Hamburguer artesanal",
                25.00,
                false,
                "burger.jpg",
                restaurantId
        );

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        MenuItem savedMenuItem = new MenuItem(
                1L,
                request.name(),
                request.description(),
                request.price(),
                request.localOnly(),
                request.photoPath(),
                restaurant
        );

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any())).thenReturn(savedMenuItem);

        MenuItemResponse response = useCaseWithMultipleValidations.createMenuItem(request);

        verify(validation1).validate(request);
        verify(validation2).validate(request);
        assertNotNull(response);
    }

    @Test
    void testValidationThrowsException() {
        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Batata",
                "Batata frita",
                12.00,
                true,
                "batata.jpg",
                1L
        );

        doThrow(new IllegalArgumentException("Campo inválido")).when(validationService).validate(request);

        assertThrows(IllegalArgumentException.class, () -> createMenuItemUseCase.createMenuItem(request));
        verify(menuItemRepository, never()).save(any());
    }
}
