package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.services.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    @CacheEvict(value = "menuItemsList", allEntries = true)
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody @Valid CreateMenuItemRequest menuItemRequest,
                                                           UriComponentsBuilder uriBuilder) {
        MenuItemResponse createdMenuItem = menuItemService.createMenuItem(menuItemRequest);
        URI uri = uriBuilder.path("/menu-items/{id}").buildAndExpand(createdMenuItem.id()).toUri();
        return ResponseEntity.created(uri).body(createdMenuItem);
    }

    @GetMapping
    @Cacheable(value = "menuItemsList")
    public Page<MenuItemResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        return menuItemService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long id) {
        MenuItemResponse response = menuItemService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "menuItemsList", allEntries = true)
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id,
                                                           @RequestBody @Valid CreateMenuItemRequest menuItemRequest) {
        MenuItemResponse menuItem = menuItemService.updateMenuItem(id, menuItemRequest);
        return ResponseEntity.ok(menuItem);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "menuItemsList", allEntries = true)
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
