package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.services.UserTypeService;
import jakarta.transaction.Transactional;
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
@RequestMapping("/userType")
public class UserTypeController {


    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<UserTypeResponse> createUserType(@RequestBody @Valid CreateUserTypeRequest userTypeRequest,
                                                           UriComponentsBuilder uriBuilder) {
        UserTypeResponse createUserType = userTypeService.createUserType(userTypeRequest);
        URI uri = uriBuilder.path("/userType/{id}").buildAndExpand(createUserType.id()).toUri();
        return ResponseEntity.created(uri).body(createUserType);
    }

    @GetMapping
    @Cacheable(value = "userTypeList")
    public Page<UserTypeResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable page) {
        Page<UserTypeResponse> userTypes = userTypeService.findAll(page);
        return userTypes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> findById(@PathVariable Long id) {
        UserTypeResponse usertype = userTypeService.findById(id);
        return ResponseEntity.ok(usertype);
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<UserTypeResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserTypeRequest userRequest) {
        UserTypeResponse userType = userTypeService.updateUser(id, userRequest);
        return ResponseEntity.ok(userType);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<?> deleteUserType(@PathVariable Long id) {
        userTypeService.deleteUserType(id);
        return ResponseEntity.noContent().build();
    }
}
