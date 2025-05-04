package fiap.tech.challenge.restaurant_manager.entites.request;

public record CreateAddressRequest (
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country
){}
