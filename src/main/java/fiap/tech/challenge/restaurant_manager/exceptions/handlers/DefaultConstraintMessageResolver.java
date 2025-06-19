package fiap.tech.challenge.restaurant_manager.exceptions.handlers;

import java.util.Map;

public class DefaultConstraintMessageResolver implements ConstraintMessageResolver {

    private static final Map<String, String> CONSTRAINT_MESSAGES = Map.of(
        "LOGIN", "Login já está cadastrado",
        "EMAIL", "Email já está cadastrado"
    );

    @Override
    public String resolveMessage(String rawMessage) {
        if (rawMessage != null && rawMessage.contains("Unique index or primary key violation")) {
            for (Map.Entry<String, String> entry : CONSTRAINT_MESSAGES.entrySet()) {
                if (rawMessage.contains(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return "Violação de integridade de dados";
    }
}