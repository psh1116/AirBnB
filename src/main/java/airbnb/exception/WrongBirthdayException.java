package airbnb.exception;

public class WrongBirthdayException extends RuntimeException {
    public WrongBirthdayException(String str) {
        super(str);
    }
}
