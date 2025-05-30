package fiap.tech.challenge.restaurant_manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.tech.challenge.restaurant_manager.entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	Optional<User> findByLoginAndPassword(String login, String Password);
}
