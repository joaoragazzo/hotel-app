package unifal.hotel.api;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.repository.mySQLHotelRepository;

@RestController
@AllArgsConstructor
public class DebugHotelController {

    @GetMapping("/debug/create-tables")
    public Boolean createTables()
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        return databaseConnection.createTables();
    }

    @GetMapping("/debug/drop-tables")
    public Boolean dropTables()
    {
        mySQLHotelRepository databaseConnection = new mySQLHotelRepository();
        return databaseConnection.dropTables();
    }

}
