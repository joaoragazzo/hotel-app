package unifal.hotel.book;

public class HotelBook {
    public final static String CREATE_TABLE_PERSON =
            // language=SQL
            "CREATE TABLE person (" +
                    "id BIGINT(11) NOT NULL UNIQUE," +
                    "name VARCHAR(255) NOT NULL, " +
                    "surname VARCHAR(255) NOT NULL, " +
                    "cellphone BIGINT(11) UNIQUE," +
                    "birthdate DATE NOT NULL," +
                    "gender VARCHAR(1) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

    public final static String CREATE_TABLE_ADDRESS =
            // language=SQL
            "CREATE TABLE address (" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE, " +
                    "person_id BIGINT(11) NOT NULL," +
                    "street VARCHAR(255) NOT NULL," +
                    "neighborhood VARCHAR(255) NOT NULL," +
                    "zipcode BIGINT(255) NOT NULL," +
                    "city VARCHAR(255) NOT NULL, " +
                    "state VARCHAR(255) NOT NULL," + 
                    "country VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_ACCOUNT =
            // language=SQL
            "CREATE TABLE account(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_CLIENT =
            // language=SQL
            "CREATE TABLE client(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_EMPLOYEE =
            //language=SQL
            "CREATE TABLE employee( " +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "hire_date DATE NOT NULL," +
                    "salary INT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_MANAGER =
            //language=SQL
            "CREATE TABLE manager(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE, " +
                    "employee_id INT NOT NULL UNIQUE," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_RECEPTIONIST =
            //language=SQL
            "CREATE TABLE receptionist(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "employee_id INT NOT NULL UNIQUE," +
                    "rating INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")";

    public final static String CREATE_TABLE_ROOM =
            // language=SQL
            "CREATE TABLE room(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "type VARCHAR(1) NOT NULL," +
                    "rent INT NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

    public final static String CREATE_TABLE_BOOKING =
            // language=SQL
            "CREATE TABLE booking(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "client_id INT NOT NULL," +
                    "room_id INT NOT NULL," +
                    "checkin_date DATE NOT NULL," +
                    "checkout_date DATE NOT NULL, " +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (client_id) REFERENCES client(id) ON UPDATE CASCADE ON DELETE CASCADE, " +
                    "FOREIGN KEY (room_id) REFERENCES  room(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String DROP_ALL_TABLES =
            // language=SQL
            "DROP TABLE receptionist, address, booking, account, client, manager, employee, room, person";

    public final static String INSERT_NEW_PEOPLE =
            // language=SQL
            "INSERT INTO person(id, name, surname, cellphone, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?);";

    public final static String INSERT_NEW_ACCOUNT =
            //language=SQL
            "INSERT INTO account(id, person_id, email, password) VALUES (NULL, ?, ?, ?);";

    public final static String INSERT_NEW_ADDRESS =
            //language=SQL
            "INSERT INTO address(id, person_id, street, neighborhood, zipcode, city, country, state) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";

    public final static String INSERT_NEW_CLIENT =
            //language=SQL
            "INSERT INTO client(id, person_id) VALUES (NULL, ?);";

    public final static String INSERT_NEW_EMPLOYEE =
            //language=SQL
            "INSERT INTO employee(id, person_id, hire_date, salary) VALUES (NULL, ?, ?, ?);";

    public final static String INSERT_NEW_RECEPTIONIST =
            //language=SQL
            "INSERT INTO receptionist(id, employee_id, rating) VALUES (NULL, ?, 0)";

    public final static String INSERT_NEW_MANAGER =
            //language=SQL
            "INSERT INTO manager(id, employee_id) VALUES (NULL, ?);";

    public final static String SELECT_ALL_MANAGERS_ID =
            //language=SQL
            "SELECT employee_id FROM manager;";

    public final static String SELECT_ALL_RECEPTIONIST_ID =
            //language=SQL
            "SELECT employee_id FROM receptionist;";

    public final static String INSERT_NEW_ROOM =
            //language=SQL
            "INSERT INTO room(id, type, rent) VALUES (NULL, ?, ?);";

    public final static String INSERT_NEW_BOOKING =
            //language=SQL
            "INSERT INTO booking(id, client_id, room_id, checkin_date, checkout_date) VALUES (NULL, ?, ?, ?, ?)";

    public final static String SELECT_ALL_BOOKINGS_BETWEEN_TWO_DATES =
            //language=SQL
            "SELECT * FROM booking WHERE room_id = ? AND NOT (checkout_date <= ? OR checkin_date >= ?);";


    /**
     * Início da utilização de query personalizadas para utilização no JPA e Jakarta
     */
    public final static String CHECK_IF_EXIST_BOOKING_CONFLICTS =
            //language=SQL
            "SELECT COUNT(b.id) > 0 FROM Booking b WHERE b.room_id = :roomId AND NOT (b.checkout_date <= :startDate OR b.checkin_date >= :endDate)";
}
