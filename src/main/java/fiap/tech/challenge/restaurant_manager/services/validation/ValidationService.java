package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;

public interface ValidationService {

	void validate(CreateUserRequest request);

}
