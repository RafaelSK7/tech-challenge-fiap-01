package fiap.tech.challenge.restaurant_manager.infrastructure.resources.login;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.login.LoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginResource {

    private final LoginController loginController;

    public LoginResource(LoginController loginController) {
        this.loginController = loginController;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        log.info("Efetuando login no sistema");
        LoginResponse loginResponse = loginController.findByLogin(loginRequest);
        log.info("Login efetuado com sucesso");
        return ResponseEntity.ok(loginResponse);
    }

}
