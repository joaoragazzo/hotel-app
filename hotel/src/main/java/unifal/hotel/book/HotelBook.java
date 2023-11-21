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
                    "country VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_ACCOUNT =
            // language=SQL
            "CREATE TABLE account(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "username VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_CLIENT =
            // language=SQL
            "CREATE TABLE client(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_EMPLOYEE =
            //language=SQL
            "CREATE TABLE employee( " +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL," +
                    "hire_date DATE NOT NULL," +
                    "salary INT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_MANAGER =
            //language=SQL
            "CREATE TABLE manager(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE, " +
                    "employee_id INT NOT NULL," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_RECEPTIONIST =
            //language=SQL
            "CREATE TABLE receptionist(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "employee_id INT NOT NULL," +
                    "rating INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")";

    public final static String CREATE_TABLE_ROOM =
            // language=SQL
            "CREATE TABLE room(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "type INT(1) NOT NULL," +
                    "rate INT NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

    public final static String CREATE_TABLE_BOOKING =
            // language=SQL
            "CREATE TABLE booking(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "client_id INT NOT NULL UNIQUE," +
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
            "INSERT INTO account(id, person_id, username, password) VALUES (NULL, ?, ?, ?);";

    public final static String INSERT_NEW_ADDRESS =
            //language=SQL
            "INSERT INTO address(id, person_id, street, neighborhood, zipcode, city, country) VALUES (NULL, ?, ?, ?, ?, ?, ?);";

}
