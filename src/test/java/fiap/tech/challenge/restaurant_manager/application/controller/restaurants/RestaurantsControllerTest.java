package fiap.tech.challenge.restaurant_manager.application.controller.restaurants;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.CreateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.DeleteRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.ReadRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.domain.usecases.restaurant.UpdateRestaurantUseCase;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.AddressEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.RestaurantEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.enums.CuisineType;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantsControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private ReadRestaurantUseCase readRestaurantUseCase;

    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Mock
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    private RestaurantEntity mockEntity;
    private CreateRestaurantRequest mockRequest;
    private RestaurantResponse mockResponse;
    private AddressEntity mockAddressEntity;
    private UsersEntity mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new UsersEntity();

        mockAddressEntity = new AddressEntity();

        mockUser.setName("name");
        mockUser.setId(1L);
        mockUser.setLogin("login");
        mockUser.setEmail("email@email.com.br");
        mockUser.setLastUpdate(LocalDateTime.now());

        mockAddressEntity.setId(1L);
        mockAddressEntity.setStreet("Rua A");
        mockAddressEntity.setNumber("2 B");
        mockAddressEntity.setNeighborhood("São Miguel");
        mockAddressEntity.setCity("Sao Paulo");
        mockAddressEntity.setState("SP");
        mockAddressEntity.setZipCode("09876543");
        mockAddressEntity.setCountry("Brasil");
        mockAddressEntity.setUser(mockUser);

        mockEntity = new RestaurantEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Restaurante Teste");
        mockEntity.setCuisineType("BRAZILIAN");
        mockEntity.setStartTime("12:00");
        mockEntity.setEndTime("22:00");
        mockEntity.setAddress(mockAddressEntity);

        mockEntity.setOwner(mockUser);

        mockRequest = new CreateRestaurantRequest("Restaurante Teste", new CreateAddressRequest("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil"), CuisineType.BRAZILIAN, "12:00", "22:00", 1L);
        mockResponse = new RestaurantResponse(1L, "Restaurante Teste", new AddressResponse("Rua A", "101", "Vila B", "Sao Paulo", "SP", "12345676", "Brasil"), "BRAZILIAN", "12:00", "22:00", 1L);
    }

    @Test
    @DisplayName("createRestaurant - Deve criar um restaurante e retornar a resposta DTO")
    void deve_criarRestaurante_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(createRestaurantUseCase.createRestaurant(any(CreateRestaurantRequest.class))).thenReturn(mockEntity);

        // Ação: Chamar o método do controller
        RestaurantResponse actualResponse = restaurantController.createRestaurant(mockRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        assertEquals(mockResponse.name(), actualResponse.name());
        verify(createRestaurantUseCase).createRestaurant(mockRequest);
    }

    @Test
    @DisplayName("findAll - Deve retornar uma página de restaurantes")
    void deve_encontrarTodosRestaurantes_quandoSolicitado() {
        // Cenário: Mock do use case para retornar uma página paginada
        Page<RestaurantResponse> expectedPage = new PageImpl<>(Collections.singletonList(mockResponse));
        when(readRestaurantUseCase.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Ação: Chamar o método do controller com um objeto Pageable
        Page<RestaurantResponse> actualPage = restaurantController.findAll(Pageable.unpaged());

        // Verificação:
        assertNotNull(actualPage);
        assertEquals(1, actualPage.getTotalElements());
        assertEquals(mockResponse.name(), actualPage.getContent().get(0).name());
        verify(readRestaurantUseCase).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById - Deve encontrar um restaurante por ID e retornar a resposta DTO")
    void deve_encontrarRestaurantePorId_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(readRestaurantUseCase.findById(anyLong())).thenReturn(mockEntity);

        // Ação: Chamar o método do controller com o ID
        RestaurantResponse actualResponse = restaurantController.findById(1L);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(mockResponse.id(), actualResponse.id());
        verify(readRestaurantUseCase).findById(1L);
    }

    @Test
    @DisplayName("findByIdEntity - Deve encontrar um restaurante por ID e retornar a entidade")
    void deve_encontrarRestauranteEntityPorId_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade esperada
        when(readRestaurantUseCase.findById(anyLong())).thenReturn(mockEntity);

        // Ação: Chamar o método do controller
        RestaurantEntity actualEntity = restaurantController.findByIdEntity(1L);

        // Verificação:
        assertNotNull(actualEntity);
        assertEquals(mockEntity.getId(), actualEntity.getId());
        verify(readRestaurantUseCase).findById(1L);
    }

    @Test
    @DisplayName("updateRestaurant - Deve atualizar um restaurante e retornar a resposta DTO")
    void deve_atualizarRestaurante_quandoSolicitado() {
        // Cenário: Mock do use case para retornar a entidade atualizada
        RestaurantEntity updatedEntity = new RestaurantEntity();
        updatedEntity.setId(1L);
        updatedEntity.setName("Restaurante Atualizado");
        updatedEntity.setAddress(mockAddressEntity);
        updatedEntity.setStartTime("13:00");
        updatedEntity.setEndTime("23:00");
        updatedEntity.setOwner(mockUser);
        CreateRestaurantRequest updateRequest = new CreateRestaurantRequest("Restaurante Atualizado", new CreateAddressRequest("Rua B", "1022", "Vila C", "Sao Paulo", "SP", "12345676", "Brasil"), CuisineType.JAPANESE, "13:00", "23:00", 1L);

        when(updateRestaurantUseCase.updateRestaurant(anyLong(), any(CreateRestaurantRequest.class))).thenReturn(updatedEntity);

        // Ação: Chamar o método do controller
        RestaurantResponse actualResponse = restaurantController.updateRestaurant(1L, updateRequest);

        // Verificação:
        assertNotNull(actualResponse);
        assertEquals(updatedEntity.getId(), actualResponse.id());
        assertEquals(updatedEntity.getName(), actualResponse.name());
        verify(updateRestaurantUseCase).updateRestaurant(1L, updateRequest);
    }

    @Test
    @DisplayName("deleteRestaurant - Deve deletar um restaurante com sucesso")
    void deve_deletarRestaurante_quandoSolicitado() {
        // Cenário: Mock do use case para não fazer nada
        doNothing().when(deleteRestaurantUseCase).deleteRestaurant(anyLong());

        // Ação: Chamar o método do controller
        restaurantController.deleteRestaurant(1L);

        // Verificação: Garantir que o método do use case foi chamado
        verify(deleteRestaurantUseCase).deleteRestaurant(1L);
    }
}
