package fiap.tech.challenge.restaurant_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiap.tech.challenge.restaurant_manager.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

//    @PostMapping
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//        // TODO validar a resposta que é feita de um login, e se iremos usar o spring security
//        User user = loginService.validateLogin(loginRequest.login(), loginRequest.password());
//
//        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getName(), user.getUserType());
//
//        return ResponseEntity.ok(loginResponse);
//    }
//
//    @PatchMapping
//    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
//
//        loginService.updatePassword(request);
//
//        return ResponseEntity.ok("Senha atualizada com sucesso");
//
//    }

}
