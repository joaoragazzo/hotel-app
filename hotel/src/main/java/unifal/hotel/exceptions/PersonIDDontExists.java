package unifal.hotel.exceptions;

public class PersonIDDontExists extends RuntimeException
{
    public PersonIDDontExists(String message)
    {
        super(message);
    }

}
