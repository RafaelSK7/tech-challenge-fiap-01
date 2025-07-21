package fiap.tech.challenge.restaurant_manager.services.usecase.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.entites.response.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public ReadMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public Page<MenuItemResponse> findAll(Pageable pageable) {
        Page<MenuItem> menuItemPage = menuItemRepository.findAll(pageable);
        List<MenuItemResponse> responseList = menuItemPage.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responseList, pageable, menuItemPage.getTotalElements());
    }

    public MenuItemResponse findById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        return toResponse(menuItem);
    }

    private MenuItemResponse toResponse(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isLocalOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId());
    }
}