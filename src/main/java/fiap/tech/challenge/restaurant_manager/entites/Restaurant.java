package fiap.tech.challenge.restaurant_manager.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

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

    @NotBlank(message = "O nome do restaurante deve estar preenchido.")
    @Column(unique = true, nullable = false)
    private String name;
    @NotBlank(message = "O tipo de cozinha deve estar preenchido")
    private String cuisineType;
    @NotBlank(message = "O horario de abertura deve estar preenchido")
    private String startTime;
    @NotBlank(message = "O horario de fechamento deve estar preenchido")
    private String endTime;
    private LocalDateTime lastUpdate;

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
