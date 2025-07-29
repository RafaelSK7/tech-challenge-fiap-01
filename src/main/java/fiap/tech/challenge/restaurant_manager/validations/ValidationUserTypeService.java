package fiap.tech.challenge.restaurant_manager.validations;

import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;

public interface ValidationUserTypeService {

    void validate(CreateUserTypeRequest request);
}
