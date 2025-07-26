package fiap.tech.challenge.restaurant_manager.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
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
        MenuItem menuItemToDelete = menuItemRepository.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        log.info("Cardapio encontrado.");
        menuItemRepository.delete(menuItemToDelete);
        log.info("cardapio removido com sucesso.");
    }
}
