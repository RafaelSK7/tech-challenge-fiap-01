package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;
import fiap.tech.challenge.restaurant_manager.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = loginService.findByLogin(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

}
