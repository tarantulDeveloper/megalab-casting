package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.SignupRequest;
import kg.bekzhan.megalab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping("/")
    public List<User> fetchAllUsers() {
        return userService.fetchAllUsers();
    }
}
