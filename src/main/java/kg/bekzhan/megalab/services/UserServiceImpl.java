package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ERole;
import kg.bekzhan.megalab.entities.RefreshToken;
import kg.bekzhan.megalab.entities.Role;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.exceptions.ResourceIsAlreadyExistException;
import kg.bekzhan.megalab.exceptions.ResourceNotFoundException;
import kg.bekzhan.megalab.exceptions.UserNotFoundException;
import kg.bekzhan.megalab.jwt.JwtUtils;
import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.payload.responses.UserInfoResponse;
import kg.bekzhan.megalab.repo.RoleRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    private final MailSender mailSender;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public MessageResponse registerUser(SignupRequest signupRequest) {


        User user = new User(
                signupRequest.getLastName(), signupRequest.getFirstName(),
                signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail()
        );

        if (userRepo.existsByUsername(user.getUsername()) || userRepo.existsByEmail(user.getEmail())) {
            throw new ResourceIsAlreadyExistException("User is already exist!");
        }

        Set<Role> roles = new HashSet<>();
        Role readerRole = roleRepo.findByName(ERole.ROLE_READER).orElseThrow(
                () -> new ResourceNotFoundException("No such role")
        );
        roles.add(readerRole);

        user.setRoles(roles);

        user.setActive(false);

        user.setActivationCode(UUID.randomUUID().toString());

        user.setPhotoPath(uploadPath + "/" + "default-profile.jpg");
        user.setOriginalPhotoName("default-profile.jpg");


        userRepo.save(user);

        String message = String.format(
                "Hello, %s %s! \n"
                        + "Welcome to our system. Visit the link to activate your account: http://localhost:8080/api/auth/activate/%s",
                user.getFirstName(),
                user.getLastName(),
                user.getActivationCode()
        );

        mailSender.send(user.getEmail(), "Activation code", message);


        return new MessageResponse("User registered successfully! Please check your email to activate account.");
    }

    @Override
    public Page<User> fetchAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return userRepo.findAll(paging);
    }

    @Override
    public ResponseEntity<UserInfoResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
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

    @Override
    public User updateUser(UserDetails userDetails, String lastName, String firstName, String username, MultipartFile file) throws IOException {
        User updatingUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new
        );
        updatingUser.setLastName(lastName);
        updatingUser.setFirstName(firstName);
        updatingUser.setUsername(username);
        if (file != null) {
            String resultFileName = createPhotoUrl(file, uploadPath);
            updatingUser.setPhotoPath(uploadPath + "/" + resultFileName);
            updatingUser.setOriginalPhotoName(resultFileName);
        }

        return userRepo.save(updatingUser);
    }

    @Override
    public MessageResponse deletePhoto(UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new
        );
        user.setPhotoPath("");
        userRepo.save(user);
        return new MessageResponse("The User's photo is removed successfully!");
    }

    @Override
    public String activateUser(String activationCode) {
        User user = userRepo.findByActivationCode(activationCode).orElseThrow(
                UserNotFoundException::new
        );
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return "<h1 style='text-align: center; font-size: 40px; margin-top: 100px; color: green;'> Hello, "
                + user.getFirstName() + " " + user.getLastName() + ", your account has been activated</h1>";
    }

    @Override
    public MessageResponse makeEditor(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(
                UserNotFoundException::new
        );
        Role adminRole = roleRepo.findByName(ERole.ROLE_EDITOR).orElseThrow(
                () -> new ResourceNotFoundException("No such role!")
        );
        user.getRoles().add(adminRole);
        userRepo.save(user);
        return new MessageResponse("User's role has been updated");
    }

    @Override
    public MessageResponse deleteUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(
                UserNotFoundException::new
        );
        userRepo.delete(user);
        return new MessageResponse("User has been deleted successfully!");
    }

    public static String createPhotoUrl(MultipartFile file, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }
}
