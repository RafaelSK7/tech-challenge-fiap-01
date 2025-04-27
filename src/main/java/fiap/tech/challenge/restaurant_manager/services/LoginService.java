package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.exceptions.LoginInvalidException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public User validateLogin(String login, String password){
        User user = userService.findByLogin(login);

        if(!user.getPassword().equals(password)){
            throw new LoginInvalidException();
        }

        return user;
    }

}
