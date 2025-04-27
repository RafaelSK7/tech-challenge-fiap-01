package fiap.tech.challenge.restaurant_manager.entites;

import fiap.tech.challenge.restaurant_manager.entites.enums.UserType;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDateTime lastUpdate;
    // TODO validar se vale a pena criar uma entidade Address, com o endereço completo, rua numero bairro cidade estado cep etc
    private String address;
    @Enumerated(EnumType.STRING)
    private UserType userType;


    public User(CreateUserRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.login = request.login();
        this.password = request.password();
        this.address = request.address();
        this.userType = request.userType();
        this.lastUpdate = LocalDateTime.now();
    }

}
