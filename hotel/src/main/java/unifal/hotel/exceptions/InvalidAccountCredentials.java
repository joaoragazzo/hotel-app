package unifal.hotel.exceptions;

public class InvalidAccountCredentials extends RuntimeException {

    public InvalidAccountCredentials(String message) {
        super(message);
    }

}
