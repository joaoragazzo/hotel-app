package unifal.hotel.services.formatters;

import org.springframework.stereotype.Component;

@Component("idFormatter")
public class IDFormatter {

    public String format(String id)
    {
        if (id == null) {
            return null;
        }

        if (id.length() < 11) {
            return "ID not valid!";
        }

        return id.substring(0, 3) + "." + id.substring(3, 6) + "." + id.substring(6, 9) + "-" + id.substring(9, 11);
    }

}
