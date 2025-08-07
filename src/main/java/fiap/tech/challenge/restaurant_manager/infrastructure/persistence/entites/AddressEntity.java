package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O logradouro e obrigatorio.")
    private String street;
    @NotBlank(message = "O numero e obrigatorio.")
    private String number;
    @NotBlank(message = "O bairro e obrigatorio.")
    private String neighborhood;
    @NotBlank(message = "A cidade e obrigatoria.")
    private String city;
    @NotBlank(message = "O Estado e obrigatorio.")
    private String state;
    @NotBlank(message = "O CEP e obrigatorio")
    private String zipCode;
    @NotBlank(message = "O pais e obrigatorio.")
    private String country;

    @OneToOne(mappedBy = "address")
    private UsersEntity user;

    public AddressEntity(CreateAddressRequest request) {

        this.street = request.street();
        this.number = request.number();
        this.neighborhood = request.neighborhood();
        this.city = request.city();
        this.state = request.state();
        this.zipCode = request.zipCode();
        this.country = request.country();

    }
}
