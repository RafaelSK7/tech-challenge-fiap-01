package fiap.tech.challenge.restaurant_manager.controllers.users;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.services.users.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		log.info("Cadastrando o usuario.");
		UserResponse createUser = userService.createUser(userRequest);
		log.info("Usuario criado com sucesso!");
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(createUser.id()).toUri();
		return ResponseEntity.created(uri).body(createUser);
	}

	@GetMapping
	@Cacheable(value="usersList")
	public Page<UserResponse> findAll(
			@PageableDefault(page = 0, size = 10, direction = Direction.ASC, sort = "id") Pageable page) {
		log.info("Buscando todos os usuarios.");
		return userService.findAll(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
		log.info("Buscando o usuario.");
		UserResponse user = userService.findById(id);
		log.info("Usuario recuperado com sucesso.");
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest userRequest) {
		log.info("Atualizando o usuario.");
		UserResponse user = userService.updateUser(id, userRequest);
		log.info("Usuario atualizado com sucesso.");
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Deletando o usuario.");
		userService.deleteUser(id);
		log.info("usuario removido com sucesso.");
		return ResponseEntity.noContent().build();
	}

}
