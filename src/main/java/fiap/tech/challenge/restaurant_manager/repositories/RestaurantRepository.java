package fiap.tech.challenge.restaurant_manager.repositories;

import fiap.tech.challenge.restaurant_manager.entites.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
