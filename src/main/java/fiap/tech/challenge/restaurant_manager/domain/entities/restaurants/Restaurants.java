package fiap.tech.challenge.restaurant_manager.domain.entities.restaurants;

import fiap.tech.challenge.restaurant_manager.domain.entities.users.Users;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Restaurants {

    @NotNull(message = "O id e obrigatorio")
    private Long id;
    @NotNull(message = "O nome e obrigatorio")
    @NotBlank(message = "O nome e obrigatorio")
    private String name;
    @NotNull(message = "O tipo de cozinha e obrigatorio")
    @NotBlank(message = "O tipo de cozinha e obrigatorio")
    private String cuisineType;
    @NotNull(message = "O horario de abertura e obrigatorio")
    @NotBlank(message = "O horario de abertura e obrigatorio")
    private String startTime;
    @NotNull(message = "O horario de fechamento e obrigatorio")
    @NotBlank(message = "O horario de fechamento e obrigatorio")
    private String endTime;

    @NotNull(message = "O endereco e obrigatorio")
    private AddressEntity address;

    @NotNull(message = "O dono e obrigatorio")
    private Users owner;

}
