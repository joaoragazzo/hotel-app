package unifal.hotel.repository;

import org.apache.commons.codec.digest.DigestUtils;
import unifal.hotel.book.HotelBook;
import unifal.hotel.api.APIMessageResponse;
import unifal.hotel.services.enums.RoomType;

import java.sql.*;
import java.util.Objects;

public class mySQLHotelRepository {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Boolean connectToMySQL()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error when searching for the JDBC driver. Try again later!");
            return Boolean.FALSE;
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel?user=root&password=");
        } catch (SQLException e) {
            System.out.println("Error when connecting to database. Try again later!");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private Boolean closeConnection()
    {
        try {
            if (Objects.nonNull(connection) && !connection.isClosed())
                this.connection.close();

            if (Objects.nonNull(statement) && !statement.isClosed())
                this.statement.close();

            if (Objects.nonNull(preparedStatement) && !preparedStatement.isClosed())
                this.preparedStatement.close();

            if (Objects.nonNull(resultSet) && !resultSet.isClosed())
                this.resultSet.isClosed();


        } catch (SQLException e) {
            System.out.println("Um erro aconteceu!");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public APIMessageResponse createTables()
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_PERSON
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_ACCOUNT
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_ADDRESS
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_EMPLOYEE
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_MANAGER
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_RECEPTIONIST
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_CLIENT
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_ROOM
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.CREATE_TABLE_BOOKING
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            response.setMessage("A error occurs when trying to create tables: " + e);
            return response;
        } finally {
            closeConnection();
        }

        response.setMessage("The tables was successfully created!");
        return response;
    }

    public APIMessageResponse dropTables()
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.DROP_ALL_TABLES
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            response.setMessage("A error occurs when trying to drop all tables: " + e);
            return response;
        } finally {
            closeConnection();
        }

        response.setMessage("All tables was successfully dropped.");
        return response;
    }

    public APIMessageResponse insertPerson(Long id, String name, String surname, Long cellphone, Date birthdate, String gender)
    {

        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_PEOPLE
            );

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setLong(4, cellphone);
            preparedStatement.setDate(5, birthdate);
            preparedStatement.setString(6, gender);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A new person was successfully created.");
                return response;
            }

        } catch (SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("cellphone")) {
                response.setMessage("The cellphone number already exists.");
                return response;
            }

            if (e.toString().contains("PRIMARY")) {
                response.setMessage("The ID number already exists.");
                return response;
            }

            System.out.println("A error occurs when trying to insert a new person: " + e);
            response.setMessage("A unknown error happened!");
            return response;
        } finally {
            closeConnection();
        }

        response.setMessage("A new person was successfully created.");
        return response;
    }

    public APIMessageResponse insertAccount(Long person_id, String username, String password)
    {

        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_ACCOUNT
            );

            preparedStatement.setLong(1, person_id);
            preparedStatement.setString(2, username);

            String hashPassword = DigestUtils.sha256Hex(password);

            preparedStatement.setString(3, hashPassword);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("Unknown error when trying to create a new account.");
                return response;
            }

            response.setMessage("New account was successfully created.");
            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`account`, CONSTRAINT `account_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to create a account to a person that is not registered.");
                return response;
            }

            if (e.toString().contains("Duplicate entry")) {
                response.setMessage("Username or ID already registered!");
                return response;
            }

            response.setMessage("A error occurs when trying to create a new account: " + e);
            return response;
        } finally {
            closeConnection();
        }

    }

    public APIMessageResponse insertNewAddress(Long person_id, String street, String neighborhood, Long zipcode, String city, String country, String state)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_ADDRESS
            );

            preparedStatement.setLong(1, person_id);
            preparedStatement.setString(2, street);
            preparedStatement.setString(3, neighborhood);
            preparedStatement.setLong(4, zipcode);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, country);
            preparedStatement.setString(7, state);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error occurs when trying to add a new address.");
                return response;
            }

            response.setMessage("A new address was successfully added.");
            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`address`, CONSTRAINT `address_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to create new address to a person that is not registered.");
                return response;
            }

            response.setMessage("A error occurs when trying to create a new address: " + e);
            return response;
        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse insertNewClient(Long person_id)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_CLIENT
            );

            preparedStatement.setLong(1, person_id);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error occurs when trying to set a new client.");
                return response;
            }

            response.setMessage("The person " + person_id.toString() + " was successfully set as a client.");
            return response;
        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`client`, CONSTRAINT `client_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to set a person that was never registered as a client.");
                return response;
            }

            response.setMessage("A error occurs when trying to set a new client: " + e);
            return response;

        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse insertNewEmployee(Long person_id, Integer salary, Date hire_date)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_EMPLOYEE
            );

            preparedStatement.setLong(1, person_id);
            preparedStatement.setDate(2, hire_date);
            preparedStatement.setInt(3, salary);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error occurs when trying to add a new address.");
                return response;
            }

            response.setMessage("The person " + person_id.toString() + " was successfully set as an employee.");

            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("a foreign key constraint fails (`hotel`.`employee`, CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to set a person that is not registered as an employee.");
                return response;
            }

            if (e.toString().contains("Duplicate entry")) {
                response.setMessage("The person " + person_id.toString() + " is already set as an employee.");
                return response;
            }

            response.setMessage("A error occurs when trying to set a new employee: " + e);
            return response;

        } finally {
            closeConnection();
        }

    }

    public APIMessageResponse insertNewReceptionist(Integer employee_id)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.SELECT_ALL_MANAGERS_ID
            );

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                response.setMessage("The employee " + employee_id.toString() + " already is a manager.");
                return response;
            }

            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_RECEPTIONIST
            );

            preparedStatement.setInt(1, employee_id);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error occurs when trying to set a new receptionist.");
                return response;
            }

            response.setMessage("The employee " + employee_id.toString() + " was successfully set as a receptionist.");
            return response;
        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`receptionist`, CONSTRAINT `receptionist_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to set a unregistered employee as an receptionist.");
                return response;
            }

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("Duplicate entry")) {
                response.setMessage("The employee " + employee_id.toString() + " already is a receptionist.");
                return response;
            }
            response.setMessage("A error occurs when trying a new receptionist: " + e);
            return response;

        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse insertNewManager(Integer employee_id)
    {

        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                HotelBook.SELECT_ALL_RECEPTIONIST_ID
            );

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                response.setMessage("The employee " + employee_id.toString() + " already is a receptionist.");
                return response;
            }

            preparedStatement = connection.prepareStatement(
                    HotelBook.INSERT_NEW_MANAGER
            );

            preparedStatement.setInt(1, employee_id);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error happened when trying to set a new manager.");
                return response;
            }

            response.setMessage("The employee " + employee_id.toString() + " was successfully set as a manager.");
            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`manager`, CONSTRAINT `manager_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("Impossible to set a unregistered employee as an manager.");
                return response;
            }

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            if (e.toString().contains("Duplicate entry")) {
                response.setMessage("The employee " + employee_id.toString() + " already is a manager.");
                return response;
            }

            response.setMessage("A error occurs when trying to set a new manager: " + e);
            return response;

        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse insertNewRoom(RoomType type, Integer rent)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                HotelBook.INSERT_NEW_ROOM
            );

            preparedStatement.setInt(1, type.getValue());
            preparedStatement.setInt(2, rent);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("A unknown error happened when trying to create a new room.");
                return response;
            }

            response.setMessage("The new room was successfully created.");
            return response;


        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            response.setMessage("A error occurs when trying to create a new room: " + e);
            return response;

        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse insertNewBooking(Integer client_id, Integer room_id, Date checkin_date, Date checkout_date)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                HotelBook.INSERT_NEW_BOOKING
            );

            preparedStatement.setInt(1, client_id);
            preparedStatement.setInt(2, room_id);
            preparedStatement.setDate(3, checkin_date);
            preparedStatement.setDate(4, checkout_date);

            int n = preparedStatement.executeUpdate();

            if (n == 0) {
                response.setMessage("Unknown error when trying to make a new booking! Try again latter!");
                return response;
            }

            response.setMessage("A new booking was successfully registered.");
            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`booking`, CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("The client ID does not exists.");
                return response;
            }

            if (e.toString().contains("Cannot add or update a child row: a foreign key constraint fails (`hotel`.`booking`, CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)")) {
                response.setMessage("The room ID does not exists.");
                return response;
            }

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            response.setMessage("A error occurs when trying to insert a new booking: " + e);
            return response;

        } finally {
            closeConnection();
        }


    }

    public APIMessageResponse roomIsAvailable(Integer room_id, Date checkin, Date checkout)
    {
        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                HotelBook.SELECT_ALL_BOOKINGS_BETWEEN_TWO_DATES
            );

            preparedStatement.setInt(1, room_id);
            preparedStatement.setDate(2, checkin);
            preparedStatement.setDate(3, checkout);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                response.setMessage("This room is not available.");
                return response;
            }

            response.setMessage("This room have is available.");
            return response;

        } catch (NullPointerException | SQLException e) {

            if (e.toString().contains("Cannot read the array length because \"input\" is null")) {
                response.setMessage("The request does not have all necessary parameters.");
                return response;
            }

            response.setMessage("A error occurs when trying to insert a new booking: " + e);
            return response;

        } finally {
            closeConnection();
        }
    }

    public APIMessageResponse populate()
    {

        APIMessageResponse response = new APIMessageResponse();

        if (!connectToMySQL()) {
            response.setMessage("Error connecting to the database.");
            return response;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.POPULATE_PERSONS_FIRST
            );

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    HotelBook.POPULATE_PERSONS_SECOND
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            response.setMessage("A error occurs when trying to populate the database: " + e);
            return response;

        } finally {
            closeConnection();
        }


        return response;
    }

}
