package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.RestaurantNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import fiap.tech.challenge.restaurant_manager.repositories.RestaurantRepository;
import fiap.tech.challenge.restaurant_manager.usecases.menuItem.UpdateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.validations.ValidateMenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateMenuItemUseCaseTest {

    private MenuItemRepository menuItemRepository;
    private RestaurantRepository restaurantRepository;
    private ValidateMenuItemService validationService1;
    private ValidateMenuItemService validationService2;
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(MenuItemRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        validationService1 = mock(ValidateMenuItemService.class);
        validationService2 = mock(ValidateMenuItemService.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(
                menuItemRepository,
                restaurantRepository,
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

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        MenuItem existingMenuItem = mock(MenuItem.class);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        doNothing().when(validationService1).validate(request);
        doNothing().when(validationService2).validate(request);
        when(menuItemRepository.save(existingMenuItem)).thenReturn(existingMenuItem);

        MenuItemResponse response = updateMenuItemUseCase.updateMenuItem(menuItemId, request);
        verify(menuItemRepository).findById(menuItemId);
        verify(restaurantRepository).findById(restaurantId);
        verify(validationService1).validate(request);
        verify(validationService2).validate(request);
        verify(existingMenuItem).setName(request.name());
        verify(existingMenuItem).setDescription(request.description());
        verify(existingMenuItem).setPrice(request.price());
        verify(existingMenuItem).setLocalOnly(request.localOnly());
        verify(existingMenuItem).setPhotoPath(request.photoPath());
        verify(existingMenuItem).setRestaurant(restaurant);
        verify(menuItemRepository).save(existingMenuItem);
        assertNotNull(response);
    }

    @Test
    void testUpdateMenuItemThrowsInvalidMenuItemExceptionWhenNotFound() {
        Long menuItemId = 999L;
        CreateMenuItemRequest request = mock(CreateMenuItemRequest.class);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.empty());
        assertThrows(InvalidMenuItemException.class,
                () -> updateMenuItemUseCase.updateMenuItem(menuItemId, request));
        verify(menuItemRepository).findById(menuItemId);
        verifyNoMoreInteractions(restaurantRepository, validationService1, validationService2, menuItemRepository);
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

        MenuItem existingMenuItem = mock(MenuItem.class);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        doNothing().when(validationService1).validate(request);
        doNothing().when(validationService2).validate(request);
        when(restaurantRepository.findById(invalidRestaurantId)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class,
                () -> updateMenuItemUseCase.updateMenuItem(menuItemId, request));
        verify(menuItemRepository).findById(menuItemId);
        verify(validationService1).validate(request);
        verify(validationService2).validate(request);
        verify(restaurantRepository).findById(invalidRestaurantId);
        verify(menuItemRepository, never()).save(any());
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

        MenuItem existingMenuItem = mock(MenuItem.class);
        Restaurant restaurant = mock(Restaurant.class);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        ValidateMenuItemService v1 = mock(ValidateMenuItemService.class);
        ValidateMenuItemService v2 = mock(ValidateMenuItemService.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(
                menuItemRepository,
                restaurantRepository,
                List.of(v1, v2)
        );

        updateMenuItemUseCase.updateMenuItem(menuItemId, request);
        verify(v1).validate(request);
        verify(v2).validate(request);
    }
}
