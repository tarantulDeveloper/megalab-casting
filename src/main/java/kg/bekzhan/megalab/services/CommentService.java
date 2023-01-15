package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.payload.requests.CommentRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {
    MessageResponse createComment(CommentRequest comment, Integer postId, UserDetails userDetails);

    MessageResponse createReply(CommentRequest comment, Integer commentId, UserDetails userDetails);
}
