package fiap.tech.challenge.restaurant_manager.entites;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserTypeRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "userTypes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;

    @NotBlank
    private String userTypeName;

    private LocalDateTime lastUpdate;

    public UserType(CreateUserTypeRequest request) {
        this.userTypeName = request.userTypeName();
        this.lastUpdate = LocalDateTime.now();
    }

}