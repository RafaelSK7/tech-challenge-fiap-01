package fiap.tech.challenge.restaurant_manager.application.validations;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;

public interface ValidationUserTypeService {

    void validate(CreateUserTypeRequest request);
}
