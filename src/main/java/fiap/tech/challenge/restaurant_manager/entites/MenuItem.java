package fiap.tech.challenge.restaurant_manager.entites;

import lombok.*;
import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "menuItem")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "A descricao é obrigatoria.")
    private String description;

    @Column(name = "price")
    @NotNull(message = "O preço é obrigatório.")
    private Double price;

    @Column(name = "localOnly")
    @NotNull(message = "O indicador de consumo no local e obrigatorio.")
    private boolean localOnly;

    @Column(name = "photoPath")
    @NotBlank(message = "A foto do prato e obrigatoria.")
    private String photoPath;

    @JoinColumn(name = "restaurants_id", referencedColumnName = "id")
    @ManyToOne()
    private Restaurant restaurant;

    public MenuItem(CreateMenuItemRequest createMenuItemRequest, Restaurant restaurant) {
        this.name = createMenuItemRequest.name();
        this.description = createMenuItemRequest.description();
        this.price = createMenuItemRequest.price();
        this.localOnly = createMenuItemRequest.localOnly();
        this.photoPath = createMenuItemRequest.photoPath();
        this.restaurant = restaurant;
    }

}
