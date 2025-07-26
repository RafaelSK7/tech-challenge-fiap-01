package fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens;

public record CreateMenuItemRequest(
    String name, 
    String description,
    Double price,
    Boolean localOnly,
    String photoPath,
    Long restaurantId
) {

}
