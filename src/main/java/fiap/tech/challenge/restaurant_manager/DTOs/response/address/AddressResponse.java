package fiap.tech.challenge.restaurant_manager.DTOs.response.address;

public record AddressResponse(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country) {
}
