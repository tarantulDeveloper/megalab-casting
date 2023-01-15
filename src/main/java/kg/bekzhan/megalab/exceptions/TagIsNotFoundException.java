package kg.bekzhan.megalab.exceptions;

public class TagIsNotFoundException extends RuntimeException{
    public TagIsNotFoundException() {
        super("Error: Tag is not found!");
    }
}
