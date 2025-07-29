package fiap.tech.challenge.restaurant_manager.validations;

import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;

public interface ValidateUserService {

	void validate(CreateUserRequest request);

}
