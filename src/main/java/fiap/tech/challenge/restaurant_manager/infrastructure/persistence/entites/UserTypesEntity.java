package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
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
public class UserTypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;

    @NotBlank(message = "O nome e obrigatorio")
    @Column(unique = true, nullable = false)
    private String userTypeName;

    private LocalDateTime lastUpdate;

    public UserTypesEntity(CreateUserTypeRequest request) {
        this.userTypeName = request.userTypeName().trim().toUpperCase();
        this.lastUpdate = LocalDateTime.now();
    }

}