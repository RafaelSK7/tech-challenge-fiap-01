package fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem;

import fiap.tech.challenge.restaurant_manager.application.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.application.gateway.menuItems.MenuItemsGateway;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteMenuItemUseCase {

    private final MenuItemsGateway menuItemsGateway;

    public DeleteMenuItemUseCase(MenuItemsGateway menuItemsGateway) {
        this.menuItemsGateway = menuItemsGateway;
    }

    public void deleteMenuItem(Long id) {
        log.info("Entrou no use case de remocao do cardapio");
        log.info("Buscando cardapio para remocao.");
        MenuItemEntity menuItemToDelete = menuItemsGateway.findById(id)
                .orElseThrow(() -> new InvalidMenuItemException(id));
        log.info("Cardapio encontrado.");
        menuItemsGateway.delete(menuItemToDelete);
        log.info("Cardapio removido com sucesso.");
    }
}
