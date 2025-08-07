package fiap.tech.challenge.restaurant_manager.domain.usecases.user;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidLogonException;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.users.UsersGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadUserUseCase {

    private UsersGateway usersGateway;

    public ReadUserUseCase(UsersGateway usersGateway) {
        this.usersGateway = usersGateway;
    }

    public Page<UserResponse> findAll(Pageable page) {
        log.info("Entrou no use case de busca de usuarios.");
        log.info("Buscando usuarios.");
        Page<UsersEntity> userPages = usersGateway.findAll(page);
        log.info("Montando DTO da lista de usuarios.");
        List<UserResponse> responseList = userPages.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        log.info("Retornando a lista.");
        return new PageImpl<>(responseList, page, userPages.getTotalElements());
    }

    public UsersEntity findById(Long id) {
        log.info("Entrou no use case de busca de usuario");
        log.info("Buscando usuario informado.");
        return usersGateway.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UsersEntity findByLoginAndPassword(LoginRequest loginRequest) {
        log.info("Entrou no use case de busca de usuario por login e senha");
        log.info("Buscando usuario informado por login e password.");
        return usersGateway.findByLoginAndPassword(loginRequest.login(), loginRequest.password()).orElseThrow(InvalidLogonException::new);
    }

    private UserResponse toResponse(UsersEntity user) {
        log.info("Montando o DTO de retorno do usuario");
        AddressResponse addressResponse = null;

        if (user.getAddress() != null) {
            addressResponse = new AddressResponse(user.getAddress().getStreet(), user.getAddress().getNumber(),
                    user.getAddress().getNeighborhood(), user.getAddress().getCity(), user.getAddress().getState(),
                    user.getAddress().getZipCode(), user.getAddress().getCountry());
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getLogin(),
                user.getUserType().getUserTypeId(), addressResponse, user.getRestaurants());
    }

}
