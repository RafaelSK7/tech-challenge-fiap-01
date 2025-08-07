package fiap.tech.challenge.restaurant_manager.domain.entities.address;

import fiap.tech.challenge.restaurant_manager.domain.entities.users.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Address {

    @NotNull(message = "O id e obrigatorio")
    private Long id;
    @NotBlank(message = "O logradouro e obrigatorio")
    private String street;
    @NotBlank(message = "O numero e obrigatorio")
    private String number;
    @NotBlank(message = "O bairro e obrigatorio")
    private String neighborhood;
    @NotBlank(message = "A cidade e obrigatorio")
    private String city;
    @NotBlank(message = "O Estado e obrigatorio")
    private String state;
    @NotBlank(message = "O CEP e obrigatorio")
    private String zipCode;
    @NotBlank(message = "O Pais e obrigatorio")
    private String country;
    @NotNull(message = "O usuario e obrigatorio")
    private Users user;
}
