package history_database;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    static final Database database = new Database("src/main/resources/database.properties");

    public static void main(String[] args) {
        DataHandler data = new DataHandler(database);


        try (Connection connection = database.getConnection()) {
            System.out.println("Database connected :) !");
        } catch (SQLException e) {
            System.out.println("Database not connected");
            throw new RuntimeException(e);
        }

    }
}
