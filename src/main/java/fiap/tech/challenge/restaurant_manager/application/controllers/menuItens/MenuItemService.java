package fiap.tech.challenge.restaurant_manager.application.controllers.menuItens;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.CreateMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.DeleteMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.ReadMenuItemUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.menuItem.UpdateMenuItemUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuItemService {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final ReadMenuItemUseCase readMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public MenuItemService(CreateMenuItemUseCase createMenuItemUseCase,
                           ReadMenuItemUseCase readMenuItemUseCase,
                           UpdateMenuItemUseCase updateMenuItemUseCase,
                           DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.readMenuItemUseCase = readMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest request) {
        log.info("Entrou no servico de cadastro do cardapio.");
        return createMenuItemUseCase.createMenuItem(request);
    }

    public Page<MenuItemResponse> findAll(Pageable page) {
        log.info("Entrou no servico de busca de todos os cardapios.");
        return readMenuItemUseCase.findAll(page);
    }

    public MenuItemResponse findById(Long id) {
        log.info("Entrou no servico de busca do cardapio.");
        return readMenuItemUseCase.findById(id);
    }

    public MenuItemResponse updateMenuItem(Long id, CreateMenuItemRequest request) {
        log.info("Entrou no servico de atualizacao do cardapio.");
        return updateMenuItemUseCase.updateMenuItem(id, request);
    }

    public void deleteMenuItem(Long id) {
        log.info("Entrou no servico de remocao do cardapio.");
        deleteMenuItemUseCase.deleteMenuItem(id);
    }
}