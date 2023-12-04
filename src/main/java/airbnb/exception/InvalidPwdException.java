package airbnb.exception;

public class InvalidPwdException extends RuntimeException {
    public InvalidPwdException(String str) {
        super(str);
    }
}
