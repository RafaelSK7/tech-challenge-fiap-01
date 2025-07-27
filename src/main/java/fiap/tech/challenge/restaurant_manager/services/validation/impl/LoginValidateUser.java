package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidLoginException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
