package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.payload.responses.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {
    MessageResponse registerUser(SignupRequest signupRequest);

    Page<User> fetchAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<UserInfoResponse> login(LoginRequest loginRequest);

    User updateUser(UserDetails userDetails, String lastName, String firstName, String username, MultipartFile file) throws IOException;

    MessageResponse deletePhoto(UserDetails userDetails);

    String activateUser(String activationCode);

    MessageResponse makeEditor(Integer userId);

    MessageResponse deleteUserById(Integer userId);
}
