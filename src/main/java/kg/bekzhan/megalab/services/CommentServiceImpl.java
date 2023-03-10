package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.Comment;
import kg.bekzhan.megalab.entities.Post;
import kg.bekzhan.megalab.entities.User;
import kg.bekzhan.megalab.exceptions.UserNotFoundException;
import kg.bekzhan.megalab.payload.requests.CommentRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.repo.CommentRepo;
import kg.bekzhan.megalab.repo.PostRepo;
import kg.bekzhan.megalab.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Transactional
    @Override
    public MessageResponse createComment(CommentRequest comment, Integer postId, UserDetails userDetails) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("No such post!"));

        Comment newComment = new Comment();
//        newComment.setPost(post);
        newComment.setDate(new Date());
        newComment.setMessage(comment.getMessage());
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new);
        newComment.setAuthorName(user.getFirstName() + " " + user.getLastName());
        post.getCommentList().add(newComment);
        postRepo.save(post);
        commentRepo.save(newComment);

        return new MessageResponse("Comment has created successfully!");
    }

    @Override
    public MessageResponse createReply(CommentRequest comment, Integer commentId, UserDetails userDetails) {
        Comment parentComment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("No such comment!"));
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                UserNotFoundException::new);
        Comment replyComment = new Comment();
        replyComment.setDate(new Date());
        replyComment.setMessage(comment.getMessage());
        replyComment.setAuthorName(user.getFirstName() + " " + user.getLastName());
        parentComment.setReply(replyComment);
        commentRepo.save(parentComment);


        return new MessageResponse("Comment has added successfully!");
    }


}
