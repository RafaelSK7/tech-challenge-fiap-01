package fiap.tech.challenge.restaurant_manager.infrastructure.resources.users;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserResource {

    private final UserController userController;

    public UserResource(UserController userController) {
        this.userController = userController;
    }

    @PostMapping
    @CacheEvict(value = "usersList", allEntries = true)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest userRequest,
                                                   UriComponentsBuilder uriBuilder) {
        log.info("Cadastrando o usuario.");
        UserResponse createUser = userController.createUser(userRequest);
        log.info("Usuario criado com sucesso!");
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(createUser.id()).toUri();
        return ResponseEntity.created(uri).body(createUser);
    }

    @GetMapping
    @Cacheable(value = "usersList")
    public Page<UserResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Direction.ASC, sort = "id") Pageable page) {
        log.info("Buscando todos os usuarios.");
        return userController.findAll(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("Buscando o usuario.");
        UserResponse user = userController.findById(id);
        log.info("Usuario recuperado com sucesso.");
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "usersList", allEntries = true)
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest userRequest) {
        log.info("Atualizando o usuario.");
        UserResponse user = userController.updateUser(id, userRequest);
        log.info("Usuario atualizado com sucesso.");
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "usersList", allEntries = true)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deletando o usuario.");
        userController.deleteUser(id);
        log.info("usuario removido com sucesso.");
        return ResponseEntity.noContent().build();
    }

}
