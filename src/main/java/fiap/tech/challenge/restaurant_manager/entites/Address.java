package fiap.tech.challenge.restaurant_manager.entites;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateAddressRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

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
    private User user;

    public Address(CreateAddressRequest request) {

        this.street = request.street();
        this.number = request.number();
        this.neighborhood = request.neighborhood();
        this.city = request.city();
        this.state = request.state();
        this.zipCode = request.zipCode();
        this.country = request.country();

    }
}
