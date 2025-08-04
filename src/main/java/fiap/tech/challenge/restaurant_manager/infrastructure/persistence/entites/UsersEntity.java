package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
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
public class UsersEntity {

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
    private UserTypesEntity userType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    public UsersEntity(CreateUserRequest request, UserTypesEntity userType) {
        this.name = request.name();
        this.email = request.email();
        this.login = request.login();
        this.password = request.password();
        this.address = new AddressEntity(request.address());
        this.userType = userType;
        this.lastUpdate = LocalDateTime.now();
    }

}
