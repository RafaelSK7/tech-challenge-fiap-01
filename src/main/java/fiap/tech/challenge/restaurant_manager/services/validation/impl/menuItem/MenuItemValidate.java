package fiap.tech.challenge.restaurant_manager.services.validation.impl.menuItem;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.CardapioException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateMenuItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuItemValidate implements ValidateMenuItemService {
    @Override
    public void validate(CreateMenuItemRequest request) {
        log.info("Validando campos do cardapio");
        validateMenuItemName(request.name());
        validateMenuItemDescription(request.description());
        validateMenuItemPrice(request.price());
        validateMenuItemLocalOnly(request.localOnly());
        validateMenuItemPhotoPath(request.photoPath());
        validateMenuItemRestaurantId(request.restaurantId());
    }

    public void validateMenuItemName(String name){
        log.info("Validando o nome do item do cardapio!");
        if(name == null || name.isEmpty()){
            throw new CardapioException("O nome do prato deve estar preenchido");
        }
    }

    public void validateMenuItemDescription(String description){
        log.info("Validando a descricao do item do cardapio!");
        if(description == null || description.isEmpty()){
            throw new CardapioException("A descricao do prato deve estar preenchida");
        }
    }

    public void validateMenuItemPrice(Double price){
        log.info("Validando a descricao do item do cardapio!");
        if(price == null || price <= 0){
            throw new CardapioException("O preço do prato deve preenchido com valor estritamente positivo");
        }
    }

    public void validateMenuItemLocalOnly(Boolean isLocalOnly){
        log.info("Validando a descricao do item do cardapio!");
        if(isLocalOnly == null){
            throw new CardapioException("A disponibilidade de consumo no local deve estar preenchida");
        }
    }

    public void validateMenuItemPhotoPath(String path){
        log.info("Validando a descricao do item do cardapio!");
        if(path == null || path.isEmpty()){
            throw new CardapioException("O link da foto do prato deve estar preenchido");
        }
    }

    public void validateMenuItemRestaurantId(Long restauranteId){
        log.info("Validando o id do restaurante ao qual o cardapio pertence");
        if(restauranteId == null || restauranteId <= 0){
            throw new CardapioException("O id do restaurante deve estar preenchido com um valor inteiro maior que 0");
        }
    }
}
