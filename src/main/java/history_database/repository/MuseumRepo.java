package history_database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import history_database.model.ItemJewelry;
import history_database.model.ItemWeapon;
import history_database.model.Museum;
import history_database.model.Person;

public class MuseumRepo {
    protected DataSource dataSource;

    public MuseumRepo() {
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

    private boolean exists(Museum museum){
        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM museum WHERE id = " + museum.getId());

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean Create(Museum museum) {

        if(exists(museum)) {
            return false;
        }

        try (Connection connection = database.getConnection()) {

            String query = "INSERT INTO museum VALUES(?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, museum.id());
            statement.setString(2, museum.name());
            statement.setString(3, museum.location());

            int update = statement.executeUpdate();

            if (update > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

       public Museum getById(int id){
        //TODO: Implement
    }
}
