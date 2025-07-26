package fiap.tech.challenge.restaurant_manager.entites.request;

public record CreateMenuItemRequest(
    String name, 
    String description,
    Double price,
    Boolean localOnly,
    String photoPath,
    Long restaurantId
) {

}
