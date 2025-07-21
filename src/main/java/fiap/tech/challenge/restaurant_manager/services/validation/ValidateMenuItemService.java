package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;

public interface ValidateMenuItemService {

    void validate(CreateMenuItemRequest request);

}
