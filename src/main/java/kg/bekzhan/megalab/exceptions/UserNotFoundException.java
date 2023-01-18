package kg.bekzhan.megalab.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User is not found!");
    }
}
