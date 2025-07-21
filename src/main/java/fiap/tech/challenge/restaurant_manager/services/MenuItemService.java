package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.services.usecase.menuItem.CreateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.menuItem.DeleteMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.menuItem.ReadMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.services.usecase.menuItem.UpdateMenuItemUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final ReadMenuItemUseCase readMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public MenuItemService(CreateMenuItemUseCase createMenuItemUseCase,
                           ReadMenuItemUseCase readMenuItemUseCase,
                           UpdateMenuItemUseCase updateMenuItemUseCase,
                           DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.readMenuItemUseCase = readMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest request) {
        return createMenuItemUseCase.createMenuItem(request);
    }

    public Page<MenuItemResponse> findAll(Pageable page) {
        return readMenuItemUseCase.findAll(page);
    }

    public MenuItemResponse findById(Long id) {
        return readMenuItemUseCase.findById(id);
    }

    public MenuItemResponse updateMenuItem(Long id, CreateMenuItemRequest request) {
        return updateMenuItemUseCase.updateMenuItem(id, request);
    }

    public void deleteMenuItem(Long id) {
        deleteMenuItemUseCase.deleteMenuItem(id);
    }
}