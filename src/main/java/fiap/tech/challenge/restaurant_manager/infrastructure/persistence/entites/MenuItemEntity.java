package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.menuItens.CreateMenuItemRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "menuItem")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório.")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio.")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Preço é obrigatório.")
    private Double price;

    @Column(name = "localOnly")
    @NotNull(message = "O indicador de consumo no local e obrigatorio.")
    private Boolean localOnly;

    @Column(name = "photoPath")
    @NotBlank(message = "A url da foto do prato e obrigatoria.")
    private String photoPath;

    @JoinColumn(name = "restaurants_id", referencedColumnName = "id")
    @ManyToOne()
    private RestaurantEntity restaurant;

    public MenuItemEntity(CreateMenuItemRequest createMenuItemRequest, RestaurantEntity restaurant) {
        this.name = createMenuItemRequest.name();
        this.description = createMenuItemRequest.description();
        this.price = createMenuItemRequest.price();
        this.localOnly = createMenuItemRequest.localOnly();
        this.photoPath = createMenuItemRequest.photoPath();
        this.restaurant = restaurant;
    }
}
