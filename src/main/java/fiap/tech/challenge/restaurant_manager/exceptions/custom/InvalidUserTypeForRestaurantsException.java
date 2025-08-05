package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidUserTypeForRestaurantsException extends RuntimeException {

    public InvalidUserTypeForRestaurantsException (){
        super("Somente usuarios com perfil de Owner podem ser donos de restaurante.");
    }
}
