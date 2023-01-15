package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
    @RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/")
    public Page<User> fetchAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return userService.fetchAllUsers(pageNo, pageSize, sortBy);
    }


    @PutMapping("/")
    public User updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("username") String username,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return userService.updateUser(userDetails, lastName, firstName, username, file);
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PutMapping("/role/{id}")
    public MessageResponse makeEditor(@PathVariable("id") Integer userId) {
        return userService.makeEditor(userId);
    }

    @DeleteMapping("/photo")
    public MessageResponse deleteProfilePhoto(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.deletePhoto(userDetails);
    }

    @PreAuthorize("hasRole('EDITOR')")
    @DeleteMapping("/{userId}")
    public MessageResponse deleteUserById(@PathVariable("userId") Integer userId) {
        return userService.deleteUserById(userId);
    }
}
