package fiap.tech.challenge.restaurant_manager.application.validations;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;

public interface ValidateUserService {

	void validate(CreateUserRequest request);

}
