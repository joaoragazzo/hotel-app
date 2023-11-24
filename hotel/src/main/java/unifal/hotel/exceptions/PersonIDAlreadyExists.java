package unifal.hotel.exceptions;

public class PersonIDAlreadyExists extends RuntimeException
{
    public PersonIDAlreadyExists(String message)
    {
        super(message);
    }

}
