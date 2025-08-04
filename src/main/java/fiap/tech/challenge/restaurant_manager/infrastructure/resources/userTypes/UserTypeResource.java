package fiap.tech.challenge.restaurant_manager.infrastructure.resources.userTypes;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import jakarta.transaction.Transactional;
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
@RequestMapping("/userTypes")
@Slf4j
public class UserTypeResource {


    private final UserTypeController userTypeController;

    public UserTypeResource(UserTypeController userTypeController) {
        this.userTypeController = userTypeController;
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<UserTypeResponse> createUserType(@RequestBody @Valid CreateUserTypeRequest userTypeRequest,
                                                           UriComponentsBuilder uriBuilder) {
        log.info("Cadastrando o tipo de usuario.");
        UserTypeResponse createUserType = userTypeController.createUserType(userTypeRequest);
        log.info("Tipo de usuario criado com sucesso!");
        URI uri = uriBuilder.path("/userTypes/{id}").buildAndExpand(createUserType.id()).toUri();
        return ResponseEntity.created(uri).body(createUserType);
    }

    @GetMapping
    @Cacheable(value = "userTypeList")
    public Page<UserTypeResponse> findAll(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC, sort = "userTypeId") Pageable page) {
        log.info("Buscando todos os tipo de usuarios.");
        Page<UserTypeResponse> userTypes = userTypeController.findAll(page);
        return userTypes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> findById(@PathVariable Long id) {
        log.info("Buscando o tipo de usuario.");
        UserTypeResponse usertype = userTypeController.findById(id);
        log.info("Tipo de usuario recuperado com sucesso.");
        return ResponseEntity.ok(usertype);
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<UserTypeResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserTypeRequest userRequest) {
        log.info("Atualizando o tipo de usuario.");
        UserTypeResponse userType = userTypeController.updateUser(id, userRequest);
        log.info("Tipo de usuario atualizado com sucesso.");
        return ResponseEntity.ok(userType);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "userTypeList", allEntries = true)
    public ResponseEntity<?> deleteUserType(@PathVariable Long id) {
        log.info("Deletando o tipo de usuario.");
        userTypeController.deleteUserType(id);
        log.info("Tipo de usuario removido com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
