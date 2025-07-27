package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.Address;
import fiap.tech.challenge.restaurant_manager.entites.request.CreateAddressRequest;

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

    public static Address getValidAddress() {
        return new Address(
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
}
