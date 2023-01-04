package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.LoginRequest;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest signupRequest);

    List<User> fetchAllUsers();

    ResponseEntity<?> login(LoginRequest loginRequest);
}
