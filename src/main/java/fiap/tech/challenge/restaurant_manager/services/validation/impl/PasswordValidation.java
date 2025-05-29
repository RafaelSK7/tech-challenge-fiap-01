package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.InvalidPasswordException;
import fiap.tech.challenge.restaurant_manager.services.validation.UpdateUserValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordValidation implements UpdateUserValidationService {
	@Override
	public void validate(CreateUserRequest request, User user) {
		// TODO Auto-generated method stub
		log.info("Validando o password");

		if (request.password().toString().equals(user.getPassword().toString())) {
			throw new InvalidPasswordException();
		}

	}

}
