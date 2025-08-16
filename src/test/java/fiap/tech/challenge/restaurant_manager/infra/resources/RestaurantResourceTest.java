package fiap.tech.challenge.restaurant_manager.infra.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.restaurants.RestaurantResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.restaurants.RestaurantController;
import fiap.tech.challenge.restaurant_manager.infrastructure.resources.restaurants.RestaurantResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static fiap.tech.challenge.restaurant_manager.utils.RestaurantUtils.getValidCreateRestaurantRequest;
import static fiap.tech.challenge.restaurant_manager.utils.RestaurantUtils.getValidRestaurantResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RestaurantResourceTest {

    @InjectMocks
    private RestaurantResource restaurantResource;

    @Mock
    private RestaurantController restaurantController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateRestaurantRequest createRestaurantRequest;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        createRestaurantRequest = getValidCreateRestaurantRequest();
        restaurantResponse = getValidRestaurantResponse();
    }

    @Test
    void testCreateRestaurant() throws Exception {
        when(restaurantController.createRestaurant(any(CreateRestaurantRequest.class)))
                .thenReturn(restaurantResponse);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRestaurantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(restaurantResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(restaurantResponse.name())))
                .andExpect(jsonPath("$.address.street", is(restaurantResponse.address().street())))
                .andExpect(jsonPath("$.address.number", is(restaurantResponse.address().number())))
                .andExpect(jsonPath("$.address.neighborhood", is(restaurantResponse.address().neighborhood())))
                .andExpect(jsonPath("$.address.city", is(restaurantResponse.address().city())))
                .andExpect(jsonPath("$.address.state", is(restaurantResponse.address().state())))
                .andExpect(jsonPath("$.address.zipCode", is(restaurantResponse.address().zipCode())))
                .andExpect(jsonPath("$.address.country", is(restaurantResponse.address().country())));
    }


    @Test
    void testFindRestaurantById() throws Exception {
        when(restaurantController.findById(eq(1L))).thenReturn(restaurantResponse);

        mockMvc.perform(get("/restaurants/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(restaurantResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(restaurantResponse.name())))
                .andExpect(jsonPath("$.address.street", is(restaurantResponse.address().street())))
                .andExpect(jsonPath("$.address.number", is(restaurantResponse.address().number())))
                .andExpect(jsonPath("$.address.neighborhood", is(restaurantResponse.address().neighborhood())))
                .andExpect(jsonPath("$.address.city", is(restaurantResponse.address().city())))
                .andExpect(jsonPath("$.address.state", is(restaurantResponse.address().state())))
                .andExpect(jsonPath("$.address.zipCode", is(restaurantResponse.address().zipCode())))
                .andExpect(jsonPath("$.address.country", is(restaurantResponse.address().country())));
    }

    @Test
    void testUpdateRestaurant() throws Exception {
        when(restaurantController.updateRestaurant(eq(1L), any(CreateRestaurantRequest.class)))
                .thenReturn(restaurantResponse);

        mockMvc.perform(put("/restaurants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRestaurantRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(restaurantResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(restaurantResponse.name())))
                .andExpect(jsonPath("$.address.street", is(restaurantResponse.address().street())))
                .andExpect(jsonPath("$.address.number", is(restaurantResponse.address().number())))
                .andExpect(jsonPath("$.address.neighborhood", is(restaurantResponse.address().neighborhood())))
                .andExpect(jsonPath("$.address.city", is(restaurantResponse.address().city())))
                .andExpect(jsonPath("$.address.state", is(restaurantResponse.address().state())))
                .andExpect(jsonPath("$.address.zipCode", is(restaurantResponse.address().zipCode())))
                .andExpect(jsonPath("$.address.country", is(restaurantResponse.address().country())));
    }

    @Test
    void testDeleteRestaurant() throws Exception {
        doNothing().when(restaurantController).deleteRestaurant(1L);

        mockMvc.perform(delete("/restaurants/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}