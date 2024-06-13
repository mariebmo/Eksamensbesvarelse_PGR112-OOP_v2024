import com.mysql.cj.jdbc.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/*
    Denne klassen er som utgangspunkt basert på koden skrevet av Marcus Alexander Dahl og git-repoet
    PGR112v24 (https://github.com/kristiania/PGR112v24/code/lectures/_22/Database.java).
*/

public class Database
{
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final Properties properties = new Properties();

    //# Konstruktør
    public Database(String filepath) {
        try {
            this.properties.load(new FileInputStream(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Sjekker at nøklene ble lastet inn fra oppgitt filbane
        if (!properties.keySet().containsAll(List.of("url", "username", "password"))) {
            System.out.print("Keys found: ");
            System.out.println(properties.keySet());

            throw new RuntimeException("Missing information to connect to database");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }
}
