package fiap.tech.challenge.restaurant_manager.domain.entities.address;

import fiap.tech.challenge.restaurant_manager.domain.entities.users.Users;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Address {

    @NotNull(message = "O id e obrigatorio")
    private Long id;
    @NotNull(message = "O logradouro e obrigatorio")
    @NotBlank(message = "O logradouro e obrigatorio")
    private String street;
    @NotNull(message = "O numero e obrigatorio")
    @NotBlank(message = "O numero e obrigatorio")
    private String number;
    @NotNull(message = "O bairro e obrigatorio")
    @NotBlank(message = "O bairro e obrigatorio")
    private String neighborhood;
    @NotNull(message = "A cidade e obrigatorio")
    @NotBlank(message = "A cidade e obrigatorio")
    private String city;
    @NotNull(message = "O Estado e obrigatorio")
    @NotBlank(message = "O Estado e obrigatorio")
    private String state;
    @NotNull(message = "O CEP e obrigatorio")
    @NotBlank(message = "O CEP e obrigatorio")
    private String zipCode;
    @NotNull(message = "O Pais e obrigatorio")
    @NotBlank(message = "O Pais e obrigatorio")
    private String country;
    @NotNull(message = "O usuario e obrigatorio")
    private Users user;
}
