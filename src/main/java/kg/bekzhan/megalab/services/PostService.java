package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    MessageResponse createPost(PostRequest post, MultipartFile photo, UserDetails user) throws IOException;

    List<Post> fetchPosts();
}
