package fiap.tech.challenge.restaurant_manager.application.exceptions.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstraintMessageConfig {
    @Bean
    public ConstraintMessageResolver constraintMessageResolver() {
        return new DefaultConstraintMessageResolver();
    }
}