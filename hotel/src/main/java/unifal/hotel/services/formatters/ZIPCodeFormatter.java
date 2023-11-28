package unifal.hotel.services.formatters;

import org.springframework.stereotype.Controller;

@Controller("ZIPCodeFormatter")
public class ZIPCodeFormatter
{

    public String format(String zipcode)
    {
        if (zipcode == null)
        {
            return null;
        }

        if (zipcode.length() < 8)
        {
            return "Invalid ZIP Code!";
        }

        return zipcode.substring(0, 5) + "-" + zipcode.substring(5, 8);
    }
}
