package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.application.validations.ValidateMenuItemService;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateMenuItemUseCaseTest {

    private MenuItemsGateway menuItemsGateway;
    private RestaurantController restaurantController;
    private ValidateMenuItemService validationService1;
    private ValidateMenuItemService validationService2;
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemsGateway = mock(MenuItemsGateway.class);
        restaurantController = mock(RestaurantController.class);
        validationService1 = mock(ValidateMenuItemService.class);
        validationService2 = mock(ValidateMenuItemService.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(
                menuItemsGateway,
                restaurantController,
                List.of(validationService1, validationService2)
        );
    }

    @Test
    void testUpdateMenuItemSuccess() {
        Long menuItemId = 1L;
        Long restaurantId = 10L;

        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Pizza Atualizada",
                "Descrição atualizada",
                45.5,
                true,
                "nova_foto.jpg",
                restaurantId
        );

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);

        MenuItemEntity existingMenuItem = mock(MenuItemEntity.class);
        when(menuItemsGateway.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantController.findByIdEntity(restaurantId)).thenReturn(restaurant);
        doNothing().when(validationService1).validate(request);
        doNothing().when(validationService2).validate(request);
        when(menuItemsGateway.save(request, restaurant)).thenReturn(existingMenuItem);

        MenuItemEntity menuItemEntity = updateMenuItemUseCase.updateMenuItem(menuItemId, request);
        verify(menuItemsGateway).findById(menuItemId);
        verify(restaurantController).findById(restaurantId);
        verify(validationService1).validate(request);
        verify(validationService2).validate(request);
        verify(existingMenuItem).setName(request.name());
        verify(existingMenuItem).setDescription(request.description());
        verify(existingMenuItem).setPrice(request.price());
        verify(existingMenuItem).setLocalOnly(request.localOnly());
        verify(existingMenuItem).setPhotoPath(request.photoPath());
        verify(existingMenuItem).setRestaurant(restaurant);
        verify(menuItemsGateway).save(request, restaurant);
        assertNotNull(menuItemEntity);
    }

    @Test
    void testUpdateMenuItemThrowsInvalidMenuItemExceptionWhenNotFound() {
        Long menuItemId = 999L;
        CreateMenuItemRequest request = mock(CreateMenuItemRequest.class);
        when(menuItemsGateway.findById(menuItemId)).thenReturn(Optional.empty());
        assertThrows(InvalidMenuItemException.class,
                () -> updateMenuItemUseCase.updateMenuItem(menuItemId, request));
        verify(menuItemsGateway).findById(menuItemId);
        verifyNoMoreInteractions(restaurantController, validationService1, validationService2, menuItemsGateway);
    }

    @Test
    void testUpdateMenuItemThrowsRestaurantNotFoundExceptionWhenRestaurantNotFound() {
        Long menuItemId = 1L;
        Long invalidRestaurantId = 999L;

        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Nome",
                "Desc",
                10.0,
                false,
                "foto.jpg",
                invalidRestaurantId
        );

        MenuItemEntity existingMenuItem = mock(MenuItemEntity.class);
        when(menuItemsGateway.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        doNothing().when(validationService1).validate(request);
        doNothing().when(validationService2).validate(request);
        when(restaurantController.findByIdEntity(invalidRestaurantId)).thenThrow(new RestaurantNotFoundException(any()));
        assertThrows(RestaurantNotFoundException.class,
                () -> updateMenuItemUseCase.updateMenuItem(menuItemId, request));
        verify(menuItemsGateway).findById(menuItemId);
        verify(validationService1).validate(request);
        verify(validationService2).validate(request);
        verify(restaurantController).findById(invalidRestaurantId);
        verify(menuItemsGateway, never()).save(any(), any());
    }

    @Test
    void testValidationsAreCalled() {
        Long menuItemId = 1L;
        Long restaurantId = 10L;

        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "Nome",
                "Desc",
                10.0,
                false,
                "foto.jpg",
                restaurantId
        );

        MenuItemEntity existingMenuItem = mock(MenuItemEntity.class);
        RestaurantEntity restaurant = mock(RestaurantEntity.class);
        when(menuItemsGateway.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantController.findByIdEntity(restaurantId)).thenReturn(restaurant);

        ValidateMenuItemService v1 = mock(ValidateMenuItemService.class);
        ValidateMenuItemService v2 = mock(ValidateMenuItemService.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(
                menuItemsGateway,
                restaurantController,
                List.of(v1, v2)
        );

        updateMenuItemUseCase.updateMenuItem(menuItemId, request);
        verify(v1).validate(request);
        verify(v2).validate(request);
    }
}
