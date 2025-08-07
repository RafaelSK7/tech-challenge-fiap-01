package fiap.tech.challenge.restaurant_manager.domain.entities.menuItems;

import fiap.tech.challenge.restaurant_manager.domain.entities.restaurants.Restaurants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MenuItems {

    @NotNull(message = "O id e obrigatorio")
    private Long id;

    @NotBlank(message = "O nome e obrigatorio")
    private String name;

    @NotBlank(message = "A descricao e obrigatorio")
    private String description;

    @NotNull(message = "Preco e obrigatorio")
    private Double price;

    @NotNull(message = "O indicativo de consumo local e obrigatorio")
    private Boolean localOnly;

    @NotBlank(message = "O caminho da foto e obrigatoria")
    private String photoPath;

    @NotNull(message = "O restaurante e obrigatorio")
    private Restaurants restaurant;
}
