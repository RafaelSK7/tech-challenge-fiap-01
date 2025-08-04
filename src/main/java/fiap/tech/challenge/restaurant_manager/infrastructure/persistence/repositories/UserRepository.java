package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    
	Optional<UsersEntity> findByLoginAndPassword(String login, String Password);
}
