package kg.bekzhan.megalab.controllers;

import io.swagger.annotations.ApiOperation;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public MessageResponse createPost(@RequestParam("header") String header,
                                      @RequestParam("text") String text,
                                      @RequestParam("tags[]") String[] tags,
                                      @RequestParam(value = "photo", required = false) MultipartFile photo,
                                      @AuthenticationPrincipal UserDetails user) throws IOException {

        return postService.createPost(header, text, tags, photo, user);
    }


    @PostMapping("/add-to-favourites/{postId}")
    public MessageResponse addPostToFavourites(@PathVariable("postId") Integer postId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        return postService.addToFavouritePost(postId, userDetails);
    }


//    @GetMapping("/")
//    public String helloUser(HttpServletRequest request) {
//        if (request.isUserInRole("ROLE_EDITOR")) {
//            return "Hello user";
//        } else {
//            return "Hello admin";
//        }
//    }

    @DeleteMapping("/{postId}")
    @ApiOperation(value = "Deleting a post", notes = "Editor users can delete any post. Reader users can delete only their own posts. Method also checks if post exist.")
    public MessageResponse deletePostById(@PathVariable("postId") Integer postId, HttpServletRequest request,
                                          @AuthenticationPrincipal UserDetails userdetails) {
        if (request.isUserInRole("ROLE_EDITOR")) {
            return postService.deletePostByIdByEditor(postId);
        } else {
            return postService.deletePostByIdByReader(postId, userdetails);
        }
    }


}
