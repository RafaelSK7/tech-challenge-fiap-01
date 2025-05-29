package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;

public interface CreateUserValidationService {

	void validate(CreateUserRequest request);

}
