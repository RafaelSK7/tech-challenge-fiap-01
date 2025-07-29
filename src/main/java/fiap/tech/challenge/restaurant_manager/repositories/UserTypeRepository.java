package fiap.tech.challenge.restaurant_manager.repositories;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    Optional<UserType> findByUserTypeName(String userTypeName);

    Optional<UserType> findByUserTypeId(Long userTypeId);
}
