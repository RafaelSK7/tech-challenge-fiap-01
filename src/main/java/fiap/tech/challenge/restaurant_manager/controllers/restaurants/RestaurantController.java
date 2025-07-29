package fiap.tech.challenge.restaurant_manager.controllers.restaurants;

import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.services.restaurants.RestaurantService;
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
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody @Valid CreateRestaurantRequest restaurantRequest,
                                                               UriComponentsBuilder uriBuilder) {
        log.info("Cadastrando o restaurante.");
        RestaurantResponse createdRestaurant = restaurantService.createRestaurant(restaurantRequest);
        log.info("Restaurante criado com sucesso!");
        URI uri = uriBuilder.path("/restaurants/{id}").buildAndExpand(createdRestaurant.id()).toUri();
        return ResponseEntity.created(uri).body(createdRestaurant);
    }

    @GetMapping
    @Cacheable(value = "restaurantsList")
    public Page<RestaurantResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        log.info("Buscando todos os restaurantes.");
        return restaurantService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable Long id) {
        log.info("Buscando o restaurante.");
        RestaurantResponse response = restaurantService.findById(id);
        log.info("Restaurante recuperado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id,
                                                               @RequestBody @Valid CreateRestaurantRequest restaurantRequest) {
        log.info("Atualizando o restaurante.");
        RestaurantResponse restaurant = restaurantService.updateRestaurant(id, restaurantRequest);
        log.info("Restaurante atualizado com sucesso.");
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.info("Deletando o restaurante.");
        restaurantService.deleteRestaurant(id);
        log.info("Restaurante removido com sucesso.");
        return ResponseEntity.noContent().build();
    }


}
