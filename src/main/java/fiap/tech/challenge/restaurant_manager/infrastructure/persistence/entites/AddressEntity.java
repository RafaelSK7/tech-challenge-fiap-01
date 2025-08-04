package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zipCode;
    @NotBlank
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
