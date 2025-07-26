package fiap.tech.challenge.restaurant_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import fiap.tech.challenge.restaurant_manager.entites.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
