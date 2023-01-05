package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.services.RefreshTokenService;
import kg.bekzhan.megalab.services.UserDetailsImpl;
import kg.bekzhan.megalab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        return refreshTokenService.refreshAccessToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return refreshTokenService.logout();
    }

}
