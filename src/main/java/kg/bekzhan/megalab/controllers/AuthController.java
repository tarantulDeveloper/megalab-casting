package kg.bekzhan.megalab.controllers;

import io.swagger.annotations.ApiOperation;
import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.payload.responses.UserInfoResponse;
import kg.bekzhan.megalab.services.RefreshTokenService;
import kg.bekzhan.megalab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @ApiOperation(value = "Login method", response = UserInfoResponse.class)
    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }

    @GetMapping("/activate/{activationCode}")
    public String activateUser(@PathVariable("activationCode") String activationCode) {
        return userService.activateUser(activationCode);
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<MessageResponse> refreshAccessToken(HttpServletRequest request) {
        return refreshTokenService.refreshAccessToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser() {
        return refreshTokenService.logout();
    }


}
