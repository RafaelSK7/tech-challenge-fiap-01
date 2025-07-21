package fiap.tech.challenge.restaurant_manager.entites;

import lombok.*;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateMenuItemRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "menuItem")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Preço é obrigatório")
    private Double price;

    @Column(name = "localOnly")
    private boolean localOnly;

    @Column(name = "photoPath")
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

    public MenuItem() {
        
    }
}
