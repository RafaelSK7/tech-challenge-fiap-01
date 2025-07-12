package fiap.tech.challenge.restaurant_manager.entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.*;
import jakarta.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode


@Entity

public class MenuItem {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private boolean localOnly;
    private String photoPath;
    
}




