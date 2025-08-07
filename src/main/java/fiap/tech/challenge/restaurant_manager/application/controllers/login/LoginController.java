package fiap.tech.challenge.restaurant_manager.application.controllers.login;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginController {

    private final UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    public LoginResponse findByLogin(LoginRequest loginRequest) {
        log.info("Efetuando login.");
        return userController.findByLoginAndPassword(loginRequest);
    }
}
