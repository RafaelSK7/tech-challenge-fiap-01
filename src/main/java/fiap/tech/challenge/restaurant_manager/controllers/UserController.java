package fiap.tech.challenge.restaurant_manager.controllers;

import java.net.URI;

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

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		UserResponse createUser = userService.createUser(userRequest);
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(createUser.id()).toUri();
		return ResponseEntity.created(uri).body(createUser);
	}

	@GetMapping
	@Cacheable(value="usersList")
	public Page<UserResponse> findAll(
			@PageableDefault(page = 0, size = 10, direction = Direction.ASC, sort = "id") Pageable page) {
		Page<UserResponse> users = userService.findAll(page);
		return users;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
		UserResponse user = userService.findById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest userRequest) {
		UserResponse user = userService.updateUser(id, userRequest);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value="usersList", allEntries = true)
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}
