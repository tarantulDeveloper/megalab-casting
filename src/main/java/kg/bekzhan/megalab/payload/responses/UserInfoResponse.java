package kg.bekzhan.megalab.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserInfoResponse {
    private int id;
    private String username;
    private List<String> roles;

}
