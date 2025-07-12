package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;

public interface ValidationUserTypeService {

    void validate(CreateUserTypeRequest request);
}
