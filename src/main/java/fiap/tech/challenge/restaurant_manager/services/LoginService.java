package fiap.tech.challenge.restaurant_manager.services;

import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;

@Service
public class LoginService {

	private final UserService userService;

	public LoginService(UserService userService) {
		this.userService = userService;
	}

	public LoginResponse findByLogin(LoginRequest loginRequest) {
		return userService.findByLoginAndPassword(loginRequest);
	}
}
