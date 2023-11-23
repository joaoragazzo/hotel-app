package unifal.hotel.services.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unifal.hotel.entity.Address;
import unifal.hotel.entity.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonAddressRegisterDTO {
    private Address address;
    private Person person;

    public void setPersonBirthdate(String birthdateStr) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = formatter.parse(birthdateStr);
            this.person.setBirthdate(birthdate);
        } catch (ParseException e) {
            return;
        }
    }
}
