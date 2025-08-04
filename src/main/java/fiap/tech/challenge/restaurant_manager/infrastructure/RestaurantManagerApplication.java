package fiap.tech.challenge.restaurant_manager.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport
@ComponentScan(basePackages = {"fiap.tech.challenge.restaurant_manager.infrastructure.resources"
							 , "fiap.tech.challenge.restaurant_manager.application.controllers"
							 , "fiap.tech.challenge.restaurant_manager.application.gateway"
							 , "fiap.tech.challenge.restaurant_manager.domain.usecases"})
public class RestaurantManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerApplication.class, args);
	}

}
