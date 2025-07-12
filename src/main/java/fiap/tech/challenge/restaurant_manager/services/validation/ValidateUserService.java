package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;

public interface ValidateUserService {

	void validate(CreateUserRequest request);

}
