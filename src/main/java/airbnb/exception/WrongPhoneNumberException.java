package airbnb.exception;

public class WrongPhoneNumberException extends RuntimeException {
    public WrongPhoneNumberException(String str) {
        super(str);
    }
}
