package fiap.tech.challenge.restaurant_manager.validations.impl.users;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidEmailException;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailValidateUser implements ValidateUserService {

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
