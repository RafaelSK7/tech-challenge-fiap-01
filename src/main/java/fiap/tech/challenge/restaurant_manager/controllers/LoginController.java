package fiap.tech.challenge.restaurant_manager.controllers;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.request.UpdatePasswordRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;
import fiap.tech.challenge.restaurant_manager.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
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
        // TODO validar a resposta que é feita de um login, e se iremos usar o spring security
        User user = loginService.validateLogin(loginRequest.login(), loginRequest.password());

        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getName(), user.getUserType());

        return ResponseEntity.ok(loginResponse);
    }

    @PatchMapping
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {

        loginService.updatePassword(request);

        return ResponseEntity.ok("Senha atualizada com sucesso");

    }

}
