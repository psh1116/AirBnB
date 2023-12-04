package airbnb.exception;

public class InvalidIdException extends NullPointerException {
    public InvalidIdException(String str) {
        super(str);
    }
}
