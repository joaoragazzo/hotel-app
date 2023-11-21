package unifal.hotel.api.debug;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.repository.mySQLHotelRepository;
import unifal.hotel.api.APIMessageResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@AllArgsConstructor
public class DebugHotelController {

    @GetMapping("/debug/create-tables")
    public APIMessageResponse createTables()
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        return databaseConnection.createTables();
    }

    @GetMapping("/debug/drop-tables")
    public APIMessageResponse dropTables()
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        return databaseConnection.dropTables();
    }

    @GetMapping("/debug/reset-tables")
    public APIMessageResponse resetTables()
    {
        APIMessageResponse response = new APIMessageResponse();

        if(!dropTables().getMessage().toLowerCase().contains("error") &&
                !createTables().getMessage().toLowerCase().contains("error")) {
            response.setMessage("The tables was successfully reset.");
            return response;
        }

        response.setMessage("Unknown error happened when trying to reset the tables.");
        return response;
    }

    @PostMapping("/debug/insert-person")
    public APIMessageResponse insertPeople(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long id = Long.parseLong(requestParams.get("id"));
        String name = requestParams.get("name");
        String surname = requestParams.get("surname");
        Long cellphone =  Long.parseLong(requestParams.get("cellphone"));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate;

        try {
            birthdate = formatter.parse(requestParams.get("birthdate"));
        } catch (ParseException e) {
            response.setMessage("Invalid birthdate format.");
            return response;
        }

        java.sql.Date birthdateSQL = new java.sql.Date(birthdate.getTime());

        String gender = requestParams.get("gender");

        return databaseConnection.insertPerson(id, name, surname, cellphone, birthdateSQL, gender);
    }

    @PostMapping("/debug/insert-account")
    public APIMessageResponse insertAccount(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long person_id = Long.parseLong(requestParams.get("person_id"));
        String username = requestParams.get("username");
        String password = requestParams.get("password");

        return databaseConnection.insertAccount(person_id, username, password);
    }

    @PostMapping("/debug/insert-address")
    public APIMessageResponse insertAddress(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long person_id = Long.parseLong(requestParams.get("person_id"));
        String street = requestParams.get("street");
        String neighborhood = requestParams.get("neighborhood");
        Long zipcode = Long.parseLong(requestParams.get("zipcode").replace("-", ""));
        String city = requestParams.get("city");
        String country = requestParams.get("country");

        return databaseConnection.insertNewAddress(person_id, street, neighborhood, zipcode, city, country);
    }

    @PostMapping("/debug/insert-client")
    public APIMessageResponse insertClient(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long person_id = Long.parseLong(requestParams.get("person_id"));

        return databaseConnection.insertNewClient(person_id);
    }

    @PostMapping("/debug/insert-employee")
    public APIMessageResponse insertEmployee(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        APIMessageResponse response = new APIMessageResponse();


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date hiredate;

        try {
            hiredate = formatter.parse(requestParams.get("hire_date"));
        } catch (NullPointerException | ParseException e) {
            response.setMessage("Invalid hire date format.");
            return response;
        }

        java.sql.Date hiredateSQL = new java.sql.Date(hiredate.getTime());
        Integer salary = Integer.parseInt(requestParams.get("salary"));
        Long person_id = Long.parseLong(requestParams.get("person_id"));

        return databaseConnection.insertNewEmployee(person_id, salary, hiredateSQL);
    }

    @PostMapping("/debug/insert-receptionist")
    public APIMessageResponse insertReceptionist(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Integer employee_id = Integer.parseInt(requestParams.get("employee_id"));

        return databaseConnection.insertNewReceptionist(employee_id);
    }

    @PostMapping("/debug/insert-manager")
    public APIMessageResponse insertManager(@RequestBody Map<String, String> requestParams)
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Integer employee_id = Integer.parseInt(requestParams.get("employee_id"));

        return databaseConnection.insertNewManager(employee_id);
    }
}
