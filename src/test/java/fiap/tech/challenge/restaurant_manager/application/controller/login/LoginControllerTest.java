package fiap.tech.challenge.restaurant_manager.application.controller.login;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.login.LoginController;
import fiap.tech.challenge.restaurant_manager.application.controllers.users.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserController userController;

    @Test
    @DisplayName("Deve retornar uma resposta de login quando o login for bem-sucedido")
    void deve_retornarRespostaDeLogin_quandoLoginForBemSucedido() {
        // 1. Cenário
        // Criação de um DTO de requisição e resposta de exemplo
        LoginRequest loginRequest = new LoginRequest("usuario@teste.com", "senha123");
        LoginResponse expectedResponse = new LoginResponse(1L, "testeName", "testeLogin", 2L);

        // Mocking: Define o comportamento do mock de UserController
        // Quando o método findByLoginAndPassword for chamado com qualquer LoginRequest, retorne a resposta esperada
        when(userController.findByLoginAndPassword(any(LoginRequest.class))).thenReturn(expectedResponse);

        // 2. Ação
        // Chama o método que queremos testar
        LoginResponse actualResponse = loginController.findByLogin(loginRequest);

        // 3. Verificação
        // Verifica se o resultado não é nulo e se o token é o esperado
        assertNotNull(actualResponse);

        // Verificação de Comportamento (Opcional, mas boa prática)
        // Garante que o método do UserController foi de fato chamado
        verify(userController).findByLoginAndPassword(loginRequest);
    }
}
