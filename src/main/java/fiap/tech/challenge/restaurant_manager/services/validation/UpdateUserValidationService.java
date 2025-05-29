package fiap.tech.challenge.restaurant_manager.services.validation;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;

public interface UpdateUserValidationService {
	
	void validate(CreateUserRequest request, User user);
}
