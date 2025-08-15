package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
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
@EqualsAndHashCode
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "O nome e obrigatorio.")
    private String name;
    @NotBlank(message = "O tipo de cozinha e obrigatoria.")
    private String cuisineType;
    @NotBlank(message = "O horario de abertura e obrigatorio.")
    private String startTime;
    @NotBlank(message = "O horario de fechamento e obrigatorio.")
    private String endTime;
    private LocalDateTime lastUpdate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private UsersEntity owner;

    public RestaurantEntity(CreateRestaurantRequest restaurantRequest, UsersEntity owner) {
        this.name = restaurantRequest.name();
        this.address = new AddressEntity(restaurantRequest.address());
        this.cuisineType = restaurantRequest.cuisineType().name();
        this.startTime = restaurantRequest.startTime();
        this.endTime = restaurantRequest.endTime();
        this.owner = owner;
    }
}
