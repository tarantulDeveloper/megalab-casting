package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ENewsTags;
import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.repo.NewsTagRepo;
import kg.bekzhan.megalab.repo.PostRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;



@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final NewsTagRepo newsTagRepo;
    private final UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public MessageResponse createPost(PostRequest post, MultipartFile photo, UserDetails user) throws IOException {
        Post newPost = new Post();
        newPost.setHeader(post.getHeader());
        newPost.setText(post.getText());
        newPost.setPublishedDate(new Date());

        if (photo == null || photo.isEmpty()) {
            newPost.setPhotoURL(uploadPath + "/" + "default-news.png");
        } else {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + photo.getOriginalFilename();
            photo.transferTo(new File(uploadPath + "/" + resultFileName));
            newPost.setPhotoURL(uploadPath + "/" + resultFileName);
        }

        List<String> strTags = post.getTags();
        Set<NewsTag> tags = new HashSet<>();

        strTags.forEach(tag -> {
            switch (tag) {
                case "SPORT" -> {
                    NewsTag sportTag = newsTagRepo.findNewsTagByTag(ENewsTags.SPORT)
                            .orElseThrow(() -> new RuntimeException("Error: Tag is not found!"));
                    tags.add(sportTag);
                }
                case "POLITICS" -> {
                    NewsTag politicsTag = newsTagRepo.findNewsTagByTag(ENewsTags.POLITICS)
                            .orElseThrow(() -> new RuntimeException("Error: Tag is not found!"));
                    tags.add(politicsTag);
                }
                case "STARS" -> {
                    NewsTag starsTag = newsTagRepo.findNewsTagByTag(ENewsTags.STARS)
                            .orElseThrow(() -> new RuntimeException("Error: Tag is not found!"));
                    tags.add(starsTag);
                }
                case "ART" -> {
                    NewsTag artTag = newsTagRepo.findNewsTagByTag(ENewsTags.ART)
                            .orElseThrow(() -> new RuntimeException("Error: Tag is not found!"));
                    tags.add(artTag);
                }
                case "FASHION" -> {
                    NewsTag fashionTag = newsTagRepo.findNewsTagByTag(ENewsTags.FASHION)
                            .orElseThrow(() -> new RuntimeException("Error: Tag is not found!"));
                    tags.add(fashionTag);
                }
            }
        });

        newPost.setTags(tags);
        User userFromDb = userRepo.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("No such user!"));
        userFromDb.getMyPosts().add(newPost);
        userRepo.save(userFromDb);
        return new MessageResponse("Post has created successfully!");
    }

    @Override
    public List<Post> fetchPosts() {
        return postRepo.findAll();
    }

    @Override
    public MessageResponse addToFavouritePost(Integer postId, UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new RuntimeException("No suh user!")
        );
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new RuntimeException("No such post!")
        );
        user.getFavouritePosts().add(post);
        userRepo.save(user);
        return new MessageResponse("Post has been added to favourites!");
    }

    @Override
    public ResponseEntity<?> deletePostByIdByEditor(Integer postId) {

        postRepo.deletePostByIdCustomMethod(postId);
        return ResponseEntity.ok().body(new MessageResponse("Post has been deleted successfully"));
    }

    @Override
    public ResponseEntity<?> deletePostByIdByReader(Integer postId, UserDetails userDetails) {
        User me = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new RuntimeException("No such user!")
        );
        Optional<Post> idMatchedPostInMyPosts = me.getMyPosts().stream().filter(post -> post.getId() == postId).findFirst();
        if(idMatchedPostInMyPosts.isPresent()) {
            postRepo.deletePostByIdCustomMethod(postId);
            return ResponseEntity.ok().body(new MessageResponse("Post has been deleted successfully"));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You can delete only your posts!"));


    }
}
