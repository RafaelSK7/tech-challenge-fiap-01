package fiap.tech.challenge.restaurant_manager.infrastructure.persistence.repositories;

import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypesEntity, Long> {

    Optional<UserTypesEntity> findByUserTypeName(String userTypeName);

    Optional<UserTypesEntity> findByUserTypeId(Long userTypeId);
}
