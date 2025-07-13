package fiap.tech.challenge.restaurant_manager.repositories;

import fiap.tech.challenge.restaurant_manager.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
