package unifal.hotel.exceptions;

public class RoomIDDontExists extends RuntimeException {

    public RoomIDDontExists(String message) {
        super(message);
    }
}
