package fiap.tech.challenge.restaurant_manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.resources.users.UserResource;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static fiap.tech.challenge.restaurant_manager.utils.UserUtils.getValidUserResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
        createUserRequest = new CreateUserRequest(
                "Nome do Usuário",
                "usuario@example.com",
                "usuario_login",
                "senha123",
                null,
                null
        );
        userResponse = getValidUserResponse();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(CreateUserRequest.class)))
                .thenReturn(userResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(userResponse.name())))
                .andExpect(jsonPath("$.email", is(userResponse.email())))
                .andExpect(jsonPath("$.login", is(userResponse.login())));
    }

    @Test
    void testFindUserById() throws Exception {
        when(userService.findById(eq(1L))).thenReturn(userResponse);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(userResponse.name())))
                .andExpect(jsonPath("$.email", is(userResponse.email())))
                .andExpect(jsonPath("$.login", is(userResponse.login())));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(eq(1L), any(CreateUserRequest.class)))
                .thenReturn(userResponse);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userResponse.id().intValue())))
                .andExpect(jsonPath("$.name", is(userResponse.name())))
                .andExpect(jsonPath("$.email", is(userResponse.email())))
                .andExpect(jsonPath("$.login", is(userResponse.login())));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }
}