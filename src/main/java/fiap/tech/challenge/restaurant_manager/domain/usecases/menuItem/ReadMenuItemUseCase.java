package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public ReadMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public Page<MenuItemResponse> findAll(Pageable pageable) {
        log.info("Entrou no use case de busca de cardapios.");
        log.info("Buscando cardapios.");
        Page<MenuItemEntity> menuItemPage = menuItemRepository.findAll(pageable);
        log.info("Montando DTO da lista de cardapios.");
        List<MenuItemResponse> responseList = menuItemPage.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Retornando a lista.");
        return new PageImpl<>(responseList, pageable, menuItemPage.getTotalElements());
    }

    public MenuItemResponse findById(Long id) {
        log.info("Entrou no use case de busca de cardapios");
        log.info("Buscando cardapio informado.");
        MenuItemEntity menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        log.info("Cardapio localizado.");
        return toResponse(menuItem);
    }

    private MenuItemResponse toResponse(MenuItemEntity menuItem) {
        log.info("Montando o DTO de retorno do cardapio");
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isLocalOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId());
    }
}