package fiap.tech.challenge.restaurant_manager.application.gateway.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantsGatewayTest {

    @InjectMocks
    private RestaurantsGateway restaurantsGateway;

    @Mock
    private RestaurantRepository restaurantRepository;

    private RestaurantEntity mockRestaurantEntity;
    private UsersEntity mockOwnerEntity;
    private CreateRestaurantRequest mockCreateRestaurantRequest;

    private AddressEntity mockAddressEntity;

    private CreateAddressRequest mockCreateAddressRequest;

    @BeforeEach
    void setUp() {
        mockOwnerEntity = new UsersEntity();
        mockOwnerEntity.setId(1L);
        mockOwnerEntity.setName("Proprietário Teste");

        mockAddressEntity = new AddressEntity();
        mockAddressEntity.setId(1L);
        mockAddressEntity.setStreet("Rua A");
        mockAddressEntity.setNumber("2 B");
        mockAddressEntity.setNeighborhood("São Miguel");
        mockAddressEntity.setCity("Sao Paulo");
        mockAddressEntity.setState("SP");
        mockAddressEntity.setZipCode("09876543");
        mockAddressEntity.setCountry("Brasil");

        mockRestaurantEntity = new RestaurantEntity();
        mockRestaurantEntity.setId(1L);
        mockRestaurantEntity.setName("Restaurante Teste");
        mockRestaurantEntity.setAddress(mockAddressEntity);

        mockCreateAddressRequest = new CreateAddressRequest("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil");

        mockCreateRestaurantRequest = new CreateRestaurantRequest("Restaurante Teste", mockCreateAddressRequest, CuisineType.BRAZILIAN, "11:00", "22:00", 1L);
    }

    @Test
    @DisplayName("save - Deve salvar um restaurante e retornar a entidade salva")
    void deve_salvarRestaurante_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade salva
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(mockRestaurantEntity);

        // Ação: Chamar o método do gateway
        RestaurantEntity savedRestaurant = restaurantsGateway.save(mockCreateRestaurantRequest, mockOwnerEntity);

        // Verificação:
        assertNotNull(savedRestaurant);
        assertEquals(mockRestaurantEntity.getId(), savedRestaurant.getId());
        verify(restaurantRepository).save(any(RestaurantEntity.class));
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de restaurantes")
    void deve_retornarPaginaDeRestaurantes_quandoChamado() {
        // Cenário: Mock do repositório para retornar uma página paginada
        Page<RestaurantEntity> expectedPage = new PageImpl<>(Collections.singletonList(mockRestaurantEntity));
        when(restaurantRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chamar o método do gateway
        Page<RestaurantEntity> actualPage = restaurantsGateway.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockRestaurantEntity.getName(), actualPage.getContent().get(0).getName());
        verify(restaurantRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve retornar um restaurante quando o ID existe")
    void deve_retornarRestaurante_quandoIdExiste() {
        // Cenário: Mock do repositório para retornar um Optional com a entidade
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(mockRestaurantEntity));

        // Ação: Chamar o método do gateway
        Optional<RestaurantEntity> optionalEntity = restaurantsGateway.findById(1L);

        // Verificação:
        assertTrue(optionalEntity.isPresent());
        assertEquals(mockRestaurantEntity.getId(), optionalEntity.get().getId());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    @DisplayName("findById - Deve retornar um Optional vazio quando o ID não existe")
    void deve_retornarOptionalVazio_quandoIdNaoExiste() {
        // Cenário: Mock do repositório para retornar um Optional vazio
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Ação: Chamar o método do gateway
        Optional<RestaurantEntity> optionalEntity = restaurantsGateway.findById(99L);

        // Verificação:
        assertTrue(optionalEntity.isEmpty());
        verify(restaurantRepository).findById(99L);
    }

    @Test
    @DisplayName("delete - Deve deletar o restaurante com sucesso")
    void deve_deletarRestaurante_quandoChamado() {
        // Cenário: Mock do repositório para não fazer nada
        doNothing().when(restaurantRepository).delete(any(RestaurantEntity.class));

        // Ação: Chamar o método do gateway
        restaurantsGateway.delete(mockRestaurantEntity);

        // Verificação: Garantir que o método do repositório foi chamado
        verify(restaurantRepository).delete(mockRestaurantEntity);
    }

    @Test
    @DisplayName("update - Deve atualizar um restaurante e retornar a entidade atualizada")
    void deve_atualizarRestaurante_quandoChamado() {
        // Cenário: Mock do repositório para retornar a entidade atualizada
        RestaurantEntity updatedRestaurant = new RestaurantEntity();
        updatedRestaurant.setId(1L);
        updatedRestaurant.setName("Restaurante Atualizado");
        updatedRestaurant.setAddress(mockAddressEntity);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(updatedRestaurant);

        // Ação: Chamar o método do gateway
        RestaurantEntity actualRestaurant = restaurantsGateway.update(updatedRestaurant);

        // Verificação:
        assertNotNull(actualRestaurant);
        assertEquals(updatedRestaurant.getId(), actualRestaurant.getId());
        assertEquals(updatedRestaurant.getName(), actualRestaurant.getName());
        verify(restaurantRepository).save(updatedRestaurant);
    }
}
