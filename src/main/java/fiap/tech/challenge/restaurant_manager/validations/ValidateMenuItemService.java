package fiap.tech.challenge.restaurant_manager.validations;

import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;

public interface ValidateMenuItemService {

    void validate(CreateMenuItemRequest request);

}
