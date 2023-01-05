package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ENewsTags;
import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.payload.requests.PostRequest;
import kg.bekzhan.megalab.repo.NewsTagRepo;
import kg.bekzhan.megalab.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final NewsTagRepo newsTagRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public Post createPost(PostRequest post, MultipartFile photo, User user) throws IOException {
        Post newPost = new Post();
        newPost.setHeader(post.getHeader());
        newPost.setText(post.getText());
        newPost.setPublishedDate(new Date());

        if (photo.isEmpty()) {
            newPost.setPhotoURL(uploadPath + "/" + "default-news.jpg");
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
        newPost.setUser(user);


        return postRepo.save(newPost);
    }
}