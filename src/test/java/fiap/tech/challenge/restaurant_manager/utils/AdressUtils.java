package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;

public class AdressUtils {

    public static CreateAddressRequest getValidCreateAddressRequest() {
        return new CreateAddressRequest(
                "Rua Exemplo",   // street
                "123",           // number
                "Bairro Exemplo",// neighborhood
                "Cidade Exemplo",// city
                "Estado Exemplo",// state
                "00000-000",     // zipCode
                "País Exemplo"   // country
        );
    }

    public static AddressEntity getValidAddress() {
        return new AddressEntity(
                1L,
                "Rua Exemplo",   // street
                "123",           // number
                "Bairro Exemplo",// neighborhood
                "Cidade Exemplo",// city
                "Estado Exemplo",// state
                "00000-000",     // zipCode
                "País Exemplo",   // country
                null
        );
    }

    public static AddressResponse getValidAddressResponse() {
        return new AddressResponse(
                "Rua Exemplo",   // street
                "123",           // number
                "Bairro Exemplo",// neighborhood
                "Cidade Exemplo",// city
                "Estado Exemplo",// state
                "00000-000",     // zipCode
                "País Exemplo"   // country
        );
    }
}
