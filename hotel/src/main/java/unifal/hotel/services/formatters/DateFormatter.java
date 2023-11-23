package unifal.hotel.services.formatters;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("dateFormatter")
public class DateFormatter {
    public String format (Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
