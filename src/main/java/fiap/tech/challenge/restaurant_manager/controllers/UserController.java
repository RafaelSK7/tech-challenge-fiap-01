package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.services.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest userRequest){
        UserResponse createUser = userService.createUser(userRequest);
        // TODO: implementar a resposta created 201
        return ResponseEntity.ok(createUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                           @RequestBody CreateUserRequest userRequest){
        UserResponse user = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
