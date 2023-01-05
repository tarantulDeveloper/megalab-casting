package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {
    Post createPost(PostRequest post, MultipartFile photo, User user) throws IOException;
}
