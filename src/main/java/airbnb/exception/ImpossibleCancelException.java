package airbnb.exception;

public class ImpossibleCancelException extends RuntimeException {
    public ImpossibleCancelException(String str) {
        super(str);
    }
}
