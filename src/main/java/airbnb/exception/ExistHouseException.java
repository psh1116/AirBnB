package airbnb.exception;

public class ExistHouseException extends RuntimeException{
    public ExistHouseException(String str) {
        super(str);
    }
}