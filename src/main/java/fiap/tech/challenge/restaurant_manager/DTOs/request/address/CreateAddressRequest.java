package fiap.tech.challenge.restaurant_manager.DTOs.request.address;

public record CreateAddressRequest (
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country
){}
