package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    MessageResponse createPost(String header, String text, String[] tags, MultipartFile photo, UserDetails user) throws IOException;

    Page<Post> fetchPosts(Integer pageNo, Integer pageSize, String sortBy);

    MessageResponse addToFavouritePost(Integer postId, UserDetails userDetails);

    MessageResponse deletePostByIdByEditor(Integer postId);

    MessageResponse deletePostByIdByReader(Integer postId, UserDetails userDetails);

    List<Post> fetchPostsByTag(String[] tags);
}
