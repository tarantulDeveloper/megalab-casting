package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.payload.requests.CommentRequest;
import kg.bekzhan.megalab.payload.responses.MessageResponse;
import kg.bekzhan.megalab.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public MessageResponse createCommentOnPost(@RequestBody CommentRequest comment, @PathVariable("postId") Integer postId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(comment, postId, userDetails);
    }

    @PostMapping("/reply/{commentId}")
    public MessageResponse createReply(@RequestBody CommentRequest comment,
                                       @PathVariable("commentId") Integer commentId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createReply(comment, commentId, userDetails);
    }

}
