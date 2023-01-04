package kg.bekzhan.megalab.payload.requests;

import lombok.Data;

@Data
public class SignupRequest {
    private String lastName;
    private String firstName;
    private String username;
    private String password;
}
