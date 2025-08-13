package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseTest {

    private MenuItemsGateway menuItemsGateway;
    private RestaurantController restaurantController;
    private ValidateMenuItemService validationService;
    private CreateMenuItemUseCase createMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsGateway = mock(MenuItemsGateway.class);
        restaurantController = mock(RestaurantController.class);
        validationService = mock(ValidateMenuItemService.class);
        createMenuItemUseCase = new CreateMenuItemUseCase(
                menuItemsGateway,
                restaurantController,
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

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);

        MenuItemEntity savedMenuItem = new MenuItemEntity(
                10L,
                request.name(),
                request.description(),
                request.price(),
                request.localOnly(),
                request.photoPath(),
                restaurant
        );

        doNothing().when(validationService).validate(request);
        when(restaurantController.findByIdEntity(restaurantId)).thenReturn(restaurant);
        when(menuItemsGateway.save(any(CreateMenuItemRequest.class), any(RestaurantEntity.class))).thenReturn(savedMenuItem);

        MenuItemEntity menuItem = createMenuItemUseCase.createMenuItem(request);

        verify(validationService).validate(request);
        verify(menuItemsGateway).save(any(CreateMenuItemRequest.class), any(RestaurantEntity.class));

        assertNotNull(menuItem);
        assertEquals(10L, menuItem.getId());
        assertEquals("Pizza", menuItem.getName());
        assertEquals("Deliciosa pizza de calabresa", menuItem.getDescription());
        assertEquals(39.90, menuItem.getPrice(), 0.001);
        assertFalse(menuItem.getLocalOnly());
        assertEquals("pizza.jpg", menuItem.getPhotoPath());
        assertEquals(restaurantId, menuItem.getRestaurant().getId());
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
        when(restaurantController.findById(restaurantId)).thenThrow(new RestaurantNotFoundException(anyLong()));

        assertThrows(RestaurantNotFoundException.class, () -> createMenuItemUseCase.createMenuItem(request));
        verify(menuItemsGateway, never()).save(any(), any());
    }

    @Test
    void testMultipleValidationsAreCalled() {
        ValidateMenuItemService validation1 = mock(ValidateMenuItemService.class);
        ValidateMenuItemService validation2 = mock(ValidateMenuItemService.class);

        CreateMenuItemUseCase useCaseWithMultipleValidations = new CreateMenuItemUseCase(
                menuItemsGateway,
                restaurantController,
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

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);

        MenuItemEntity savedMenuItem = new MenuItemEntity(
                1L,
                request.name(),
                request.description(),
                request.price(),
                request.localOnly(),
                request.photoPath(),
                restaurant
        );

        when(restaurantController.findByIdEntity(restaurantId)).thenReturn(restaurant);
        when(menuItemsGateway.save(any(), any())).thenReturn(savedMenuItem);

        MenuItemEntity menuItem = useCaseWithMultipleValidations.createMenuItem(request);

        verify(validation1).validate(request);
        verify(validation2).validate(request);
        assertNotNull(menuItem);
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
        verify(menuItemsGateway, never()).save(any(), any());
    }
}
