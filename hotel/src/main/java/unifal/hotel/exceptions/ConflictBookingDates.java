package unifal.hotel.exceptions;

import java.util.Date;

public class ConflictBookingDates extends RuntimeException
{

    public ConflictBookingDates(String message)
    {
        super(message);
    }


}
