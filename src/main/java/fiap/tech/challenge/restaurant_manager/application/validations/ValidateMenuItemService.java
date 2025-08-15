package fiap.tech.challenge.restaurant_manager.application.validations;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;

public interface ValidateMenuItemService {

    void validate(CreateMenuItemRequest request);

}
