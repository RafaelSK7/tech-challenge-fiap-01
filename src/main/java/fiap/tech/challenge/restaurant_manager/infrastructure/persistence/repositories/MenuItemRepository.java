package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.MenuItemEntity;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
}
