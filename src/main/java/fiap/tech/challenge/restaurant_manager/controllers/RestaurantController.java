package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.services.RestaurantService;
import fiap.tech.challenge.restaurant_manager.services.UserService;
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
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody @Valid CreateRestaurantRequest restaurantRequest,
                                                               UriComponentsBuilder uriBuilder) {
        RestaurantResponse createdRestaurant = restaurantService.createRestaurant(restaurantRequest);
        URI uri = uriBuilder.path("/restaurants/{id}").buildAndExpand(createdRestaurant.id()).toUri();
        return ResponseEntity.created(uri).body(createdRestaurant);
    }

    @GetMapping
    @Cacheable(value = "restaurantsList")
    public Page<RestaurantResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        return restaurantService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable Long id) {
        RestaurantResponse response = restaurantService.findById(id);
        return ResponseEntity.ok(response);
    }


}
