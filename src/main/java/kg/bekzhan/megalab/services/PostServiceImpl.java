package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ENewsTags;
import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.exceptions.ResourceNotFoundException;
import kg.bekzhan.megalab.exceptions.TagIsNotFoundException;
import kg.bekzhan.megalab.exceptions.UserNotFoundException;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.repo.NewsTagRepo;
import kg.bekzhan.megalab.repo.PostRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final NewsTagRepo newsTagRepo;
    private final UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public MessageResponse createPost(String header, String text, String[] mytags, MultipartFile photo, UserDetails user) throws IOException {
        Post newPost = new Post();
        newPost.setHeader(header);
        newPost.setText(text);
        newPost.setPublishedDate(new Date());

        if (photo == null || photo.isEmpty()) {
            newPost.setPhotoURL(uploadPath + "/" + "default-news.jpg");
            newPost.setOriginalPhotoName("default-news.jpg");
        } else {
            String resultFileName = UserServiceImpl.createPhotoUrl(photo, uploadPath);
            newPost.setPhotoURL(uploadPath + "/" + resultFileName);
            newPost.setOriginalPhotoName(resultFileName);
        }

        Set<NewsTag> tags = new HashSet<>();


        Arrays.asList(mytags).forEach(tag -> {
            switch (tag) {
                case "SPORT" -> {
                    NewsTag sportTag = newsTagRepo.findNewsTagByTag(ENewsTags.SPORT)
                            .orElseThrow(TagIsNotFoundException::new);
                    tags.add(sportTag);
                }
                case "POLITICS" -> {
                    NewsTag politicsTag = newsTagRepo.findNewsTagByTag(ENewsTags.POLITICS)
                            .orElseThrow(TagIsNotFoundException::new);
                    tags.add(politicsTag);
                }
                case "STARS" -> {
                    NewsTag starsTag = newsTagRepo.findNewsTagByTag(ENewsTags.STARS)
                            .orElseThrow(TagIsNotFoundException::new);
                    tags.add(starsTag);
                }
                case "ART" -> {
                    NewsTag artTag = newsTagRepo.findNewsTagByTag(ENewsTags.ART)
                            .orElseThrow(TagIsNotFoundException::new);
                    tags.add(artTag);
                }
                case "FASHION" -> {
                    NewsTag fashionTag = newsTagRepo.findNewsTagByTag(ENewsTags.FASHION)
                            .orElseThrow(TagIsNotFoundException::new);
                    tags.add(fashionTag);
                }
            }
        });

        newPost.setTags(tags);
        User userFromDb = userRepo.findByUsername(user.getUsername()).orElseThrow(
                UserNotFoundException::new);
        newPost.setAuthorName(userFromDb.getFirstName() + " " + userFromDb.getLastName());
        userFromDb.getMyPosts().add(newPost);
        userRepo.save(userFromDb);
        return new MessageResponse("Post has created successfully!");
    }

    @Override
    public Page<Post> fetchPosts(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return postRepo.findAll(paging);
    }

    @Override
    public MessageResponse addToFavouritePost(Integer postId, UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new
        );
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("No such post!")
        );
        user.getFavouritePosts().add(post);
        userRepo.save(user);
        return new MessageResponse("Post has been added to favourites!");
    }

    @Override
    public MessageResponse deletePostByIdByEditor(Integer postId) {

        postRepo.deletePostByIdCustomMethod(postId);
        return new MessageResponse("Post has been deleted successfully");
    }

    @Override
    public MessageResponse deletePostByIdByReader(Integer postId, UserDetails userDetails) {
        User me = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new
        );
        Optional<Post> idMatchedPostInMyPosts = me.getMyPosts().stream().filter(post -> post.getId() == postId).findFirst();
        if (idMatchedPostInMyPosts.isPresent()) {
            postRepo.deletePostByIdCustomMethod(postId);
            return new MessageResponse("Post has been deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You can delete only your own posts!")).getBody();


    }

    @Override
    public List<Post> fetchPostsByTag(String[] tags) {
        Set<NewsTag> newsTags = new HashSet<>();
        Arrays.asList(tags).forEach(tag -> newsTags.add(newsTagRepo.findNewsTagByTag(ENewsTags.valueOf(tag)).orElseThrow(
                TagIsNotFoundException::new
        )));

        return postRepo.findByTagsIn(new ArrayList<>(newsTags));
    }
}
