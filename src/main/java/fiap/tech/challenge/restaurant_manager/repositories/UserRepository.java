package fiap.tech.challenge.restaurant_manager.repositories;

import fiap.tech.challenge.restaurant_manager.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	Optional<User> findByLoginAndPassword(String login, String Password);
}
