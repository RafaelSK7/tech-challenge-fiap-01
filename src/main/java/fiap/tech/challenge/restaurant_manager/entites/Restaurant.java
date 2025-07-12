package fiap.tech.challenge.restaurant_manager.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateRestaurantRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;
    @NotBlank
    private String cuisineType;
    @NotBlank
    private String startTime;
    @NotBlank
    private String endTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;

    public Restaurant(CreateRestaurantRequest restaurantRequest, User owner) {
        this.name = restaurantRequest.name();
        this.address = new Address(restaurantRequest.address());
        this.cuisineType = restaurantRequest.cuisineType().name();
        this.startTime = restaurantRequest.startTime();
        this.endTime = restaurantRequest.endTime();
        this.owner = owner;
    }
}
