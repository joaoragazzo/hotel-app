package unifal.hotel.services.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unifal.hotel.entity.Account;
import unifal.hotel.entity.Address;
import unifal.hotel.entity.Employee;
import unifal.hotel.entity.Person;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterDTO {

    private Person person;
    private Address address;
    private Account account;
    private Employee employee;
    private String confirm_password;


}
