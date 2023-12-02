package unifal.hotel.exceptions;


public class BookingIdDontExists extends RuntimeException {

    public BookingIdDontExists(String message) {
        super(message);
    }
}
