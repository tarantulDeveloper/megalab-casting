package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,allowCredentials = "true")
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public Post createPost(@RequestPart("post") PostRequest post,
                           @RequestPart(value = "photo", required = false) MultipartFile photo,
                           @AuthenticationPrincipal User user) throws IOException {
        return postService.createPost(post, photo, user);
    }
}
