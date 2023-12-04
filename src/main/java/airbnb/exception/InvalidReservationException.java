package airbnb.exception;

public class InvalidReservationException extends RuntimeException{
    public InvalidReservationException(String str) {
        super(str);
    }
}
