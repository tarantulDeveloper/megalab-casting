package kg.bekzhan.megalab.exceptions;

public class ResourceIsAlreadyExistException extends RuntimeException {
    public ResourceIsAlreadyExistException(String message) {
        super(message);
    }
}
