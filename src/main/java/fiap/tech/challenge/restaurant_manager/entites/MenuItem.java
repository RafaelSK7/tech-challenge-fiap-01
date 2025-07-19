package fiap.tech.challenge.restaurant_manager.entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "menuItem")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode


public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id") 
    private Long id;


    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório")
    private String name;


    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio")
    private String description;


    @Column(name = "price")
    @NotBlank(message = "Preço é obrigatório")
    private Double price;

    
    @Column(name = "localOnly")
    private boolean localOnly;


    @Column(name = "photoPath")
    private String photoPath;    
}
