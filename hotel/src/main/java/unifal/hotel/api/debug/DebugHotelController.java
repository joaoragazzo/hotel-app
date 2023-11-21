package unifal.hotel.api.debug;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.repository.mySQLHotelRepository;
import unifal.hotel.api.APIMessageResponse;
import unifal.hotel.services.enums.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

        if (!requestParams.keySet().containsAll(Arrays.asList("id", "cellphone", "name", "surname", "gender", "birthdate"))) {
            response.setMessage("id, cellphone, name, surname, gender and birthdate is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long id;
        Long cellphone;
        Date birthdate;
        String name = requestParams.get("name");
        String surname = requestParams.get("surname");
        String gender = requestParams.get("gender");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            id = Long.parseLong(requestParams.get("id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid ID number.");
            return response;
        }

        try {
            cellphone =  Long.parseLong(requestParams.get("cellphone"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid phone-number.");
            return response;
        }

        try {
            birthdate = formatter.parse(requestParams.get("birthdate"));
        } catch (ParseException e) {
            response.setMessage("Invalid birthdate format.");
            return response;
        }

        java.sql.Date birthdateSQL = new java.sql.Date(birthdate.getTime());

        return databaseConnection.insertPerson(id, name, surname, cellphone, birthdateSQL, gender);
    }

    @PostMapping("/debug/insert-account")
    public APIMessageResponse insertAccount(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("person_id", "username", "password"))) {
            response.setMessage("person_id, username and password is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long person_id;
        String username = requestParams.get("username");
        String password = requestParams.get("password");

        try {
            person_id = Long.parseLong(requestParams.get("person_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid ID number.");
            return response;
        }


        return databaseConnection.insertAccount(person_id, username, password);
    }

    @PostMapping("/debug/insert-address")
    public APIMessageResponse insertAddress(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("person_id", "zipcode", "street", "neighborhood", "city", "country"))) {
            response.setMessage("person_id, zipcode, street, neighborhood, city and country is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long zipcode;
        Long person_id = Long.parseLong(requestParams.get("person_id"));
        String street = requestParams.get("street");
        String neighborhood = requestParams.get("neighborhood");
        String city = requestParams.get("city");
        String country = requestParams.get("country");

        try {
            zipcode = Long.parseLong(requestParams.get("zipcode").replace("-", ""));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid ZIP number.");
            return response;
        }

        return databaseConnection.insertNewAddress(person_id, street, neighborhood, zipcode, city, country);
    }

    @PostMapping("/debug/insert-client")
    public APIMessageResponse insertClient(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("person_id"))) {
            response.setMessage("person_id is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Long person_id;

        try {
            person_id = Long.parseLong(requestParams.get("person_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid ID number.");
            return response;
        }

        return databaseConnection.insertNewClient(person_id);
    }

    @PostMapping("/debug/insert-employee")
    public APIMessageResponse insertEmployee(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("person_id","salary","hire_date"))) {
            response.setMessage("person_id, salary and hire_date is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date hiredate;
        Integer salary;
        Long person_id;

        try {
            hiredate = formatter.parse(requestParams.get("hire_date"));
        } catch (NullPointerException | ParseException e) {
            response.setMessage("Invalid hire date format.");
            return response;
        }

        java.sql.Date hiredateSQL = new java.sql.Date(hiredate.getTime());

        try {
            salary = Integer.parseInt(requestParams.get("salary"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid salary number.");
            return response;
        }

        try {
            person_id = Long.parseLong(requestParams.get("person_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid ID number.");
            return response;
        }

        return databaseConnection.insertNewEmployee(person_id, salary, hiredateSQL);
    }

    @PostMapping("/debug/insert-receptionist")
    public APIMessageResponse insertReceptionist(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("employee_id"))) {
            response.setMessage("employee_id is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Integer employee_id;

        try {
            employee_id = Integer.parseInt(requestParams.get("employee_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid employee ID.");
            return response;
        }

        return databaseConnection.insertNewReceptionist(employee_id);
    }

    @PostMapping("/debug/insert-manager")
    public APIMessageResponse insertManager(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("employee_id"))) {
            response.setMessage("employee_id is needed for this request.");
            return response;
        }

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Integer employee_id;

        try {
            employee_id = Integer.parseInt(requestParams.get("employee_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid employee ID.");
            return response;
        }

        return databaseConnection.insertNewManager(employee_id);
    }

    @PostMapping("/debug/insert-room")
    public APIMessageResponse insertRoom(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("room_type", "rent"))) {
            response.setMessage("room_type and rent is needed for this request.");
            return response;
        }


        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();

        Integer room_type;
        Integer rent;

        try {
            room_type = Integer.parseInt(requestParams.get("room_type"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid room type.");
            return response;
        }

        try {
            rent = Integer.parseInt(requestParams.get("rent"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid rent value.");
            return response;
        }

        RoomType roomTypeEnum;

        if (room_type == 1) {
            roomTypeEnum = RoomType.SUITE;
        } else if (room_type == 2) {
            roomTypeEnum = RoomType.NORMAL;
        } else if (room_type == 3) {
            roomTypeEnum = RoomType.DOUBLE;
        } else {
            response.setMessage("Unknown room type.");
            return response;
        }

        return databaseConnection.insertNewRoom(roomTypeEnum, rent);
    }

    @PostMapping("/debug/insert-booking")
    public APIMessageResponse insertBooking(@RequestBody Map<String, String> requestParams)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!requestParams.keySet().containsAll(Arrays.asList("client_id", "room_id", "checkin_date", "checkout_date"))) {
            response.setMessage("client_id, room_id, checkin_date and checkout_date is needed for this request.");
            return response;
        }

        Date checkin, checkout;
        Integer room_id;
        Integer client_id;

        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            checkin = formatter.parse(requestParams.get("checkin_date"));
            checkout = formatter.parse(requestParams.get("checkout_date"));
        } catch (NullPointerException | ParseException e) {
            response.setMessage("Invalid checkin/checkout date format.");
            return response;
        }

        java.sql.Date checkinSQL = new java.sql.Date(checkin.getTime());
        java.sql.Date checkoutSQL = new java.sql.Date(checkout.getTime());

        try {
            room_id = Integer.parseInt(requestParams.get("room_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid room ID.");
            return response;
        }

        try {
            client_id =  Integer.parseInt(requestParams.get("client_id"));
        } catch (NumberFormatException e) {
            response.setMessage("Invalid client ID.");
            return response;
        }


        APIMessageResponse response_ = databaseConnection.roomIsAvailable(room_id, checkinSQL, checkoutSQL);

        if (response_.getMessage().contains("not"))
            return response_;

        return databaseConnection.insertNewBooking(client_id, room_id, checkinSQL, checkoutSQL);

    }
}
