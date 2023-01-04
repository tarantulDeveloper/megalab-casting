package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.RefreshToken;
import kg.bekzhan.megalab.exceptions.TokenRefreshException;
import kg.bekzhan.megalab.repo.RefreshTokenRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwtRefreshExpirationInHours}")
    private long jwtRefreshExpirationInHours;

    private final RefreshTokenRepo refreshTokenRepo;
    private final UserRepo userRepo;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken generateRefreshToken(Integer userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepo.findById(userId).get());
        refreshToken.setExpiryDate(ZonedDateTime.now(ZoneId.systemDefault()).plusHours(jwtRefreshExpirationInHours));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isAfter(ZonedDateTime.now())) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
    }


}
