package kg.bekzhan.megalab.payload;

import lombok.Data;

@Data
public class SignupRequest {
    private String lastName;
    private String firstName;
    private String nickName;
    private String password;
}
