package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidEmailException;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailValidation implements ValidationService {

	private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,6}$";

	@Override
	public void validate(CreateUserRequest userRequest) {
		log.info("Validando email");
		if ((userRequest.email() == null) || (userRequest.email().isEmpty())
				|| (!userRequest.email().matches(EMAIL_REGEX))) {
			throw new InvalidEmailException();
		}
	}
}
