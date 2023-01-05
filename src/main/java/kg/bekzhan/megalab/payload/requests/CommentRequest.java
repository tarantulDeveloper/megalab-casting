package kg.bekzhan.megalab.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.SecondaryTable;

@Data
public class CommentRequest {
private String message;
}
