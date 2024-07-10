package history_database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import history_database.model.ItemJewelry;
import history_database.model.Person;

public class PersonRepo {
    protected DataSource dataSource;

    public PersonRepo() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3307/Funn");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");

        this.dataSource = mysqlDataSource;
    }

    private boolean exists(Person person){
        try (Connection con = dataSource.getConnection()) {
        Statement s = con.createStatement();
        ResultSet resultSet = s.executeQuery("SELECT * FROM person WHERE id = " + person.getId());

        return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean create(Person person) {
        if (exists(person)) {
            return false;
        }

        try (Connection connection = database.getConnection()) {

            String query = "INSERT INTO person VALUES(?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, person.id());
            statement.setString(2, person.name());
            statement.setInt(3, person.phone_number());
            statement.setString(4, person.email());

            int update = statement.executeUpdate();

            if (update > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<Person> getAll() {
        try (Connection connection = database.getConnection()) {
            ArrayList<Person> peopleArray = new ArrayList<>();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

            while (resultSet.next()) {
                var person = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("navn"),
                        resultSet.getInt("tlf"),
                        resultSet.getString("e_post")
                );
                peopleArray.add(person);
            }

            return peopleArray;
        } catch (SQLException e) {
            System.out.println("Could not load people from database");
            throw new RuntimeException(e);
        }
    }

    public Person getById(int id){
        //TODO: Implement
    }
}
