package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.RefreshToken;
import kg.bekzhan.megalab.exceptions.TokenRefreshException;
import kg.bekzhan.megalab.jwt.JwtUtils;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.repo.RefreshTokenRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwtRefreshExpirationInHours}")
    private long jwtRefreshExpirationInHours;

    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtUtils jwtUtils;
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
        if (token.getExpiryDate().isBefore(ZonedDateTime.now())) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
    }

    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);
        if((refreshToken != null) && (refreshToken.length() > 0)) {
            return findByToken(refreshToken)
                    .map(this::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    }).orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh token is empty!"));

    }


    public ResponseEntity<?> logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(principle.toString(), "anonymousUser")) {
            Integer userId = ((UserDetailsImpl) principle).getId();
            deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
