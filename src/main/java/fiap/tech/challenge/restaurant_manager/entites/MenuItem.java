package fiap.tech.challenge.restaurant_manager.entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode


@Entity

public class MenuItem {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name is Required")
    private String name;


    @Column(name = "description")
    @NotBlank(message = "Name is Required")
    private String description;


    @Column(name = "price")
    @NotBlank(message = "Name is Required")
    private Double price;

    
    @Column(name = "localOnly")
    private boolean localOnly;


    @NotBlank(message = "Name is Required")
    @Column("name = photoPath")
    private String photoPath;
    
}




