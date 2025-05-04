package fiap.tech.challenge.restaurant_manager.entites.response;

public record AddressResponse(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country) {
}
