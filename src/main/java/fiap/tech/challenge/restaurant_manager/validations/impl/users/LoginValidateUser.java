package fiap.tech.challenge.restaurant_manager.validations.impl.users;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidLoginException;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginValidateUser implements ValidateUserService {

	@Override
	public void validate(CreateUserRequest userRequest) {
		log.info("Validando o login");
		if ((userRequest.login() == null) || (userRequest.login().isEmpty())) {
			throw new InvalidLoginException();
		}

	}
}
