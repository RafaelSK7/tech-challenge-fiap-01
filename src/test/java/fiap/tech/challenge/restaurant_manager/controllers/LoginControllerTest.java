package fiap.tech.challenge.restaurant_manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fiap.tech.challenge.restaurant_manager.controllers.login.LoginController;
import fiap.tech.challenge.restaurant_manager.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.services.login.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

        loginRequest = new LoginRequest(
                "usuario",
                "senha"
        );

        loginResponse = new LoginResponse(1L, "usuario", "usuario", null );
    }

    @Test
    void testLoginSuccess() throws Exception {
        when(loginService.findByLogin(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }
}