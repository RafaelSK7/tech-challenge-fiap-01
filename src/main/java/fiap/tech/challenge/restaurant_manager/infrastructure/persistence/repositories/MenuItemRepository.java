package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories;


import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
}
