package fiap.tech.challenge.restaurant_manager.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @Email(message = "Formato do e-mail inválido")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Usuário de login é obrigatório")
    @Column(unique = true, nullable = false)
    private String login;
    @NotBlank(message = "Senha de login é obrigatório")
    @Column(nullable = false)
    private String password;
    private LocalDateTime lastUpdate;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userType_userTypeId", referencedColumnName = "userTypeId")
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Restaurant> restaurants = new ArrayList<>();

    public User(CreateUserRequest request, UserType userType) {
        this.name = request.name();
        this.email = request.email();
        this.login = request.login();
        this.password = request.password();
        this.address = new Address(request.address());
        this.userType = userType;
        this.lastUpdate = LocalDateTime.now();
    }

}
