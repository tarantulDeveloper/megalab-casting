package kg.bekzhan.megalab.repo;

import kg.bekzhan.megalab.entities.ERole;
import kg.bekzhan.megalab.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole role);
}
