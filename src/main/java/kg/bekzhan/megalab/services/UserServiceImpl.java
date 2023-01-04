package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ERole;
import kg.bekzhan.megalab.entities.RefreshToken;
import kg.bekzhan.megalab.entities.Role;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.jwt.JwtUtils;
import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.payload.responses.UserInfoResponse;
import kg.bekzhan.megalab.repo.RoleRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (userRepo.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("User is already exist.");
        }

        User user = new User(
                signupRequest.getLastName(), signupRequest.getFirstName(),
                signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();
        Role readerRole = roleRepo.findByName(ERole.ROLE_READER).orElseThrow(
                () -> new RuntimeException("No such role")
        );
        roles.add(readerRole);

        user.setRoles(roles);

        user.setPhotoPath(uploadPath + "/" + "default-profile.jpg");


        userRepo.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        roles));


    }
}
