package fiap.tech.challenge.restaurant_manager.domain.usecases.userType;

import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.application.gateway.userTypes.UserTypesGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadUserTypeUseCase {

    private UserTypesGateway userTypeGateway;

    public ReadUserTypeUseCase(UserTypesGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public Page<UserTypeResponse> findAll(Pageable page) {
        log.info("Entrou no use case de busca de tipo de usuarios.");
        log.info("Buscando tipo de usuarios.");
        Page<UserTypesEntity> userPages = userTypeGateway.findAll(page);
        log.info("Montando DTO da lista de tipo de usuarios.");
        List<UserTypeResponse> responseList = userPages.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        log.info("Retornando a lista.");
        return new PageImpl<>(responseList, page, userPages.getTotalElements());
    }

    public UserTypesEntity findByUserTypeId(Long id) {
        log.info("Entrou no use case de busca de tipo de usuario");
        log.info("Buscando tipo de usuario informado.");
        return userTypeGateway.findByUserTypeId(id).orElseThrow(() -> new UserTypeNotFoundException(id));
    }

    public UserTypesEntity findByIdEntity(Long id){
        log.info("Entrou no use case de busca de entidade.");
        log.info("Buscando tipo de usuario informado.");
        return userTypeGateway.findByUserTypeId(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }

    public Optional<UserTypesEntity> findDuplicateUserType(String userTypeName) {
        log.info("Entrou no use case de busca de tipo de usuario duplicado");
        log.info("Buscando o nome do tipo de usuario informado.");
        return userTypeGateway.findByUserTypeName(userTypeName.trim().toUpperCase());
    }

    private UserTypeResponse toResponse(UserTypesEntity userType) {
        log.info("Montando o DTO de retorno do tipo de usuario");
        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }
}
