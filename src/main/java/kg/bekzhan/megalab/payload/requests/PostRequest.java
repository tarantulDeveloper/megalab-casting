package kg.bekzhan.megalab.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructorl
public class PostRequest {
    private String header;
    private String text;
    private List<String> tags;
}
