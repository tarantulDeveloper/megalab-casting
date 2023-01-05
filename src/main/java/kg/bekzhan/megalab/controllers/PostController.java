package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.Comment;
import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public MessageResponse createPost(@RequestPart("post") PostRequest post,
                                      @RequestPart(value = "photo", required = false) MultipartFile photo,
                                      @AuthenticationPrincipal UserDetails user) throws IOException {
        return postService.createPost(post, photo, user);
    }

    @GetMapping
    public List<Post> fetchPosts() {
        return postService.fetchPosts();
    }


}
