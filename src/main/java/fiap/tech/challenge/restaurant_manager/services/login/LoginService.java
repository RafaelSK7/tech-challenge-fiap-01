package fiap.tech.challenge.restaurant_manager.services.login;

import fiap.tech.challenge.restaurant_manager.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.login.LoginResponse;

@Service
@Slf4j
public class LoginService {

	private final UserService userService;

	public LoginService(UserService userService) {
		this.userService = userService;
	}

	public LoginResponse findByLogin(LoginRequest loginRequest) {
		log.info("Efetuando login.");
		return userService.findByLoginAndPassword(loginRequest);
	}
}
