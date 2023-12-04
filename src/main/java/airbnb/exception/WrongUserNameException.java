package airbnb.exception;

public class WrongUserNameException extends RuntimeException {
    public WrongUserNameException(String str) {
        super(str);
    }
}
