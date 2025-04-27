package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.LoginInvalidException;
import fiap.tech.challenge.restaurant_manager.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = loginService.validateLogin(loginRequest.login(), loginRequest.password());

        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getName(), user.getUserType());

        return ResponseEntity.ok(loginResponse);
    }
}
