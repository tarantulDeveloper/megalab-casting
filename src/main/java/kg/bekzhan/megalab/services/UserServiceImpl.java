package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ERole;
import kg.bekzhan.megalab.entities.Role;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.SignupRequest;
import kg.bekzhan.megalab.repo.RoleRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (userRepo.existsByNickName(signupRequest.getNickName())) {
            throw new RuntimeException("User is already exist.");
        }

        User user = new User(
                signupRequest.getLastName(), signupRequest.getFirstName(),
                signupRequest.getNickName(), signupRequest.getPassword()
        );

        Set<Role> roles = new HashSet<>();
        Role readerRole = roleRepo.findByName(ERole.ROLE_READER).orElseThrow(
                () -> new RuntimeException("No such role")
        );
        roles.add(readerRole);

        user.setRoles(roles);

        user.setPhotoPath(uploadPath + "/" + "default-profile.jpg");


        return userRepo.save(user);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }
}
