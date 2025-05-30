package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.InvalidLoginException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginValidation implements ValidationService {

	@Override
	public void validate(CreateUserRequest userRequest) {
		log.info("Validando o login");
		if ((userRequest.login() == null) || (userRequest.login().isEmpty())) {
			throw new InvalidLoginException();
		}

	}
}
