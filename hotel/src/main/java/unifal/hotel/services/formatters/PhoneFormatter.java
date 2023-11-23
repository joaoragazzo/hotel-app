package unifal.hotel.services.formatters;

import org.springframework.stereotype.Component;

@Component("phoneFormatter")
public class PhoneFormatter {

    public String format(String phone)
    {
        if (phone == null) {
            return null;
        }

        if (phone.length() < 11) {
            return "Invalid phone number!";
        }

        return "(" + phone.substring(0, 2) + ") " + phone.substring(2, 3) + "." +
                phone.substring(3,7) + "-" + phone.substring(7,11);
    }

}
