package kg.bekzhan.megalab.repo;

import kg.bekzhan.megalab.entities.RefreshToken;
import kg.bekzhan.megalab.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Boolean existsByUser(User user);

    RefreshToken findByUser(User user);

    @Modifying
    int deleteByUser(User user);
}
