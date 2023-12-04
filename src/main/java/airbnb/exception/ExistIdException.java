package airbnb.exception;

public class ExistIdException extends RuntimeException {
    public ExistIdException(String str) {
        super(str);
    }
}
