package fiap.tech.challenge.restaurant_manager.infrastructure.resources.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/restaurants")
@Slf4j
public class RestaurantResource {

    private final RestaurantController restaurantController;

    public RestaurantResource(RestaurantController restaurantController) {
        this.restaurantController = restaurantController;
    }

    @PostMapping
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody @Valid CreateRestaurantRequest restaurantRequest,
                                                               UriComponentsBuilder uriBuilder) {
        log.info("Cadastrando o restaurante.");
        RestaurantResponse createdRestaurant = restaurantController.createRestaurant(restaurantRequest);
        log.info("Restaurante criado com sucesso!");
        URI uri = uriBuilder.path("/restaurants/{id}").buildAndExpand(createdRestaurant.id()).toUri();
        return ResponseEntity.created(uri).body(createdRestaurant);
    }

    @GetMapping
    @Cacheable(value = "restaurantsList")
    public Page<RestaurantResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        log.info("Buscando todos os restaurantes.");
        return restaurantController.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable Long id) {
        log.info("Buscando o restaurante.");
        RestaurantResponse response = restaurantController.findById(id);
        log.info("Restaurante recuperado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id,
                                                               @RequestBody @Valid CreateRestaurantRequest restaurantRequest) {
        log.info("Atualizando o restaurante.");
        RestaurantResponse restaurant = restaurantController.updateRestaurant(id, restaurantRequest);
        log.info("Restaurante atualizado com sucesso.");
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.info("Deletando o restaurante.");
        restaurantController.deleteRestaurant(id);
        log.info("Restaurante removido com sucesso.");
        return ResponseEntity.noContent().build();
    }


}
