package fiap.tech.challenge.restaurant_manager.controllers.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiap.tech.challenge.restaurant_manager.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.services.login.LoginService;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        log.info("Efetuando login no sistema");
        LoginResponse loginResponse = loginService.findByLogin(loginRequest);
        log.info("Login efetuado com sucesso");
        return ResponseEntity.ok(loginResponse);
    }

}
