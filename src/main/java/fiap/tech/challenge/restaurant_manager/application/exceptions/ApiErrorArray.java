package fiap.tech.challenge.restaurant_manager.application.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a estrutura padrão de resposta de erro da API.
 * Esta classe é utilizada para encapsular informações detalhadas sobre erros
 * que ocorrem durante o processamento de requisições na API REST.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorArray {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;
    private String path;
}
