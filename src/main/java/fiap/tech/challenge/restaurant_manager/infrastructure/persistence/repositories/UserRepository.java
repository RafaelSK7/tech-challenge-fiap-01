package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    
	Optional<UsersEntity> findByLoginAndPassword(String login, String Password);
}
