package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public DeleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void deleteMenuItem(Long id) {
        log.info("Entrou no use case de remocao do cardapio");
        log.info("Buscando cardapio para remocao.");
        MenuItemEntity menuItemToDelete = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        log.info("Cardapio encontrado.");
        menuItemRepository.delete(menuItemToDelete);
        log.info("Cardapio removido com sucesso.");
    }
}
