package unifal.hotel.repository;

import unifal.hotel.book.HotelBook;

import java.sql.*;
import java.util.Objects;

public class mySQLHotelRepository implements HotelRepository {

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

    public Boolean createTables()
    {
        if(!this.connectToMySQL()) {
            return Boolean.FALSE;
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
            System.out.println("A error occurs when trying to create tables: " + e);
            return Boolean.FALSE;
        } finally {
            closeConnection();
        }

        return Boolean.TRUE;
    }

    public Boolean dropTables()
    {
        if(!connectToMySQL()) {
            return Boolean.FALSE;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    HotelBook.DROP_ALL_TABLES
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("A error occurs when trying to create tables: " + e);
            return Boolean.FALSE;
        } finally {
            closeConnection();
        }

        return Boolean.TRUE;
    }


}
