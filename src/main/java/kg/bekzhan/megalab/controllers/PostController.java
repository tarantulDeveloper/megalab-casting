package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.repo.PostRepo;
import kg.bekzhan.megalab.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepo postRepo;

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

    @PostMapping("/add-to-favourites/{postId}")
    public MessageResponse addPostToFavourites(@PathVariable("postId") Integer postId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        return postService.addToFavouritePost(postId, userDetails);
    }


    @GetMapping("/")
    public String helloUser(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_EDITOR")) {
            return "Hello user";
        } else {
            return "Hello admin";
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable("postId") Integer postId, HttpServletRequest request,
                                            @AuthenticationPrincipal UserDetails userdetails) {

        postRepo.findById(postId).orElseThrow(
                () -> new RuntimeException("No such post!")
        );

        if (request.isUserInRole("ROLE_EDITOR")) {
            return postService.deletePostByIdByEditor(postId);
        } else {

            return postService.deletePostByIdByReader(postId, userdetails);
        }
    }


}
