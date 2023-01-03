package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.SignupRequest;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserService {
    User registerUser(SignupRequest signupRequest);

    List<User> fetchAllUsers();
}
