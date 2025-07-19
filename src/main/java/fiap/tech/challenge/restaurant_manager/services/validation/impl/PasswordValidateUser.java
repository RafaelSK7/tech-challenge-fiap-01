package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidPasswordException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidateUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordValidateUser implements ValidateUserService {
	@Override
	public void validate(CreateUserRequest request) {
		// TODO Auto-generated method stub
		log.info("Validando o password");

		if (request.password() == null || request.password().isEmpty()) {
			throw new InvalidPasswordException();
		}

	}

}
