package fiap.tech.challenge.restaurant_manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.tech.challenge.restaurant_manager.DTOs.request.menuItens.CreateMenuItemRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.menuItens.MenuItemResponse;
import fiap.tech.challenge.restaurant_manager.controllers.menuItens.MenuItemController;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidMenuItemException;
import fiap.tech.challenge.restaurant_manager.services.menuItens.MenuItemService;
import fiap.tech.challenge.restaurant_manager.utils.MenuItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MenuItemControllerTest {

    @InjectMocks
    private MenuItemController menuItemController;

    @Mock
    private MenuItemService menuItemService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateMenuItemRequest createMenuItemRequest;
    private MenuItemResponse menuItemResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController)
                .setCustomArgumentResolvers(new org.springframework.data.web.PageableHandlerMethodArgumentResolver())
                .build();
        createMenuItemRequest = MenuItemUtils.getValidCreateMenuItemRequest();
        menuItemResponse = MenuItemUtils.getValidMenuItemResponse();
    }

    @Test
    void testCreateMenuItem() throws Exception {
        when(menuItemService.createMenuItem(any(CreateMenuItemRequest.class)))
                .thenReturn(menuItemResponse);

        mockMvc.perform(post("/menu-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMenuItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(menuItemResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(menuItemResponse.name())));
    }

    @Test
    void testCreateMenuItemValidationError() throws Exception {
        when(menuItemService.createMenuItem(any(CreateMenuItemRequest.class)))
                .thenThrow(new InvalidMenuItemException());

        mockMvc.perform(post("/menu-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMenuItemRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindMenuItemById() throws Exception {
        when(menuItemService.findById(eq(1L))).thenReturn(menuItemResponse);

        mockMvc.perform(get("/menu-items/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(menuItemResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(menuItemResponse.name())));
    }

    @Test
    void testFindMenuItemByIdNotFound() throws Exception {
        when(menuItemService.findById(eq(99L))).thenThrow(new InvalidMenuItemException(99L));

        mockMvc.perform(get("/menu-items/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllMenuItems() throws Exception {
        Page<MenuItemResponse> page = new PageImpl<>(List.of(menuItemResponse));
        when(menuItemService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/menu-items")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(menuItemResponse.id().intValue())))
                .andExpect(jsonPath("$.content[0].name", is(menuItemResponse.name())));
    }

    @Test
    void testUpdateMenuItem() throws Exception {
        when(menuItemService.updateMenuItem(eq(1L), any(CreateMenuItemRequest.class)))
                .thenReturn(menuItemResponse);

        mockMvc.perform(put("/menu-items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMenuItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(menuItemResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(menuItemResponse.name())));
    }

    @Test
    void testUpdateMenuItemNotFound() throws Exception {
        when(menuItemService.updateMenuItem(eq(99L), any(CreateMenuItemRequest.class)))
                .thenThrow(new InvalidMenuItemException(99L));

        mockMvc.perform(put("/menu-items/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMenuItemRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMenuItem() throws Exception {
        doNothing().when(menuItemService).deleteMenuItem(1L);

        mockMvc.perform(delete("/menu-items/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(menuItemService).deleteMenuItem(1L);
    }

    @Test
    void testDeleteMenuItemNotFound() throws Exception {
        doThrow(new InvalidMenuItemException(99L)).when(menuItemService).deleteMenuItem(99L);

        mockMvc.perform(delete("/menu-items/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}