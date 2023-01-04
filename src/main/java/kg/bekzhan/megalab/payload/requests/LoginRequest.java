package kg.bekzhan.megalab.payload.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
