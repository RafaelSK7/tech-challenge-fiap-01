//package fiap.tech.challenge.restaurant_manager.infrastructure.resources.menuItens;
//
//import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
//import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
//import fiap.tech.challenge.restaurant_manager.application.controllers.menuItens.MenuItemService;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/menu-items")
//@Slf4j
//public class MenuItemResource {
//
//    private final MenuItemService menuItemService;
//
//    public MenuItemResource(MenuItemService menuItemService) {
//        this.menuItemService = menuItemService;
//    }
//
//    @PostMapping
//    @CacheEvict(value = "menuItemsList", allEntries = true)
//    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody @Valid CreateMenuItemRequest menuItemRequest,
//                                                           UriComponentsBuilder uriBuilder) {
//        log.info("Cadastrando o cardapio.");
//        MenuItemResponse createdMenuItem = menuItemService.createMenuItem(menuItemRequest);
//        log.info("Cardapio criado com sucesso!");
//        URI uri = uriBuilder.path("/menu-items/{id}").buildAndExpand(createdMenuItem.id()).toUri();
//        return ResponseEntity.created(uri).body(createdMenuItem);
//    }
//
//    @GetMapping
//    @Cacheable(value = "menuItemsList")
//    public Page<MenuItemResponse> findAll(
//            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
//        log.info("Buscando todos os cardapios.");
//        return menuItemService.findAll(pageable);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long id) {
//        log.info("Buscando o cardapio.");
//        MenuItemResponse response = menuItemService.findById(id);
//        log.info("Cardapio recuperado com sucesso.");
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    @CacheEvict(value = "menuItemsList", allEntries = true)
//    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id,
//                                                           @RequestBody @Valid CreateMenuItemRequest menuItemRequest) {
//        log.info("Atualizando o cardapio.");
//        MenuItemResponse menuItem = menuItemService.updateMenuItem(id, menuItemRequest);
//        log.info("Cardapio atualizado com sucesso.");
//        return ResponseEntity.ok(menuItem);
//    }
//
//    @DeleteMapping("/{id}")
//    @CacheEvict(value = "menuItemsList", allEntries = true)
//    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
//        log.info("Deletando o cardapio.");
//        menuItemService.deleteMenuItem(id);
//        log.info("Cardapio removido com sucesso.");
//        return ResponseEntity.noContent().build();
//    }
//}
