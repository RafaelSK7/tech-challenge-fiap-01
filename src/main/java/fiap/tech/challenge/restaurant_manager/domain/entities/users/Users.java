package fiap.tech.challenge.restaurant_manager.domain.entities.users;

import fiap.tech.challenge.restaurant_manager.domain.entities.userTypes.UserTypes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Users {

    @NotNull(message = "O id e obrigatorio")
    private Long id;
    @NotNull(message = "O nome e obrigatorio")
    @NotBlank(message = "O nome e obrigatorio")
    private String name;
    @NotNull(message = "O email e obrigatorio")
    @NotBlank(message = "O email e obrigatorio")
    @Email(message = "Formato do e-mail invalido")
    private String email;
    @NotNull(message = "O login e obrigatorio")
    @NotBlank(message = "O login e obrigatorio")
    private String login;
    @NotNull(message = "A senha e obrigatoria")
    @NotBlank(message = "A senha e obrigatoria")
    private String password;
    @NotNull(message = "O identificador de tipo de usuario e obrigatorio")
    private UserTypes userTypeId;
}
