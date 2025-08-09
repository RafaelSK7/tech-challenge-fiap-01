package fiap.tech.challenge.restaurant_manager.entites;

import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import jakarta.persistence.*;
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
    @Column(unique = true, nullable = false)
    private String userTypeName;

    private LocalDateTime lastUpdate;

    public UserType(CreateUserTypeRequest request) {
        this.userTypeName = request.userTypeName().trim().toUpperCase();
        this.lastUpdate = LocalDateTime.now();
    }

}