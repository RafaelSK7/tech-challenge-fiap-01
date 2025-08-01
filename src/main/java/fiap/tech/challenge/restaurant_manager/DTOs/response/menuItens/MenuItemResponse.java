package fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens;

public record MenuItemResponse(
    
    Long id,
    String name, 
    String description,
    Double price,
    Boolean localOnly,
    String photoPath,
    Long restaurantId              
    
    ) {

}
