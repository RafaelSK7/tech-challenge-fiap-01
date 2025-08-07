package fiap.tech.challenge.restaurant_manager.domain.entities.userTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTypes {

    @NotNull(message = "O id e obrigatorio")
    private Long userTypeId;

    @NotBlank(message = "O nome e obrigatorio")
    private String userTypeName;

}
