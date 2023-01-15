package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {
    private final PostService postService;


    @GetMapping("/posts")
    public Page<Post> fetchPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "publishedDate") String sortBy
    ) {
        return postService.fetchPosts(pageNo, pageSize, sortBy);
    }

    @GetMapping("/posts/category")
    public List<Post> fetchPostsByTag(@RequestParam("tags[]") String[] tags) {
        return postService.fetchPostsByTag(tags);
    }
}
