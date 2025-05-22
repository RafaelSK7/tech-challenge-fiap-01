package fiap.tech.challenge.restaurant_manager.services.validation.impl;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.services.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordValidation implements ValidationService {@Override
	public void validate(CreateUserRequest request) {
		// TODO Auto-generated method stub
		log.info("Validando o password");
	}

}
