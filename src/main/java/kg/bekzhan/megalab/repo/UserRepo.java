package kg.bekzhan.megalab.repo;

import kg.bekzhan.megalab.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByNickName(String nickname);
    Boolean existsByNickName(String nickname);
}
