package fiap.tech.challenge.restaurant_manager.entites;

import fiap.tech.challenge.restaurant_manager.entites.request.CreateUserRequest;
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

    public Address(CreateUserRequest request) {

        this.street = request.address().street();
        this.number = request.address().number();
        this.neighborhood = request.address().neighborhood();
        this.city = request.address().city();
        this.state = request.address().state();
        this.zipCode = request.address().zipCode();
        this.country = request.address().country();

    }
}
