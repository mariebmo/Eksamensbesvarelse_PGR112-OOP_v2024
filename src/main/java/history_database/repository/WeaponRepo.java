package history_database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import history_database.model.ItemCoin;
import history_database.model.ItemJewelry;
import history_database.model.ItemWeapon;
import history_database.model.Person;

public class WeaponRepo {
    protected DataSource dataSource;

    public WeaponRepo() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3307/Funn");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");

        this.dataSource = mysqlDataSource;
    }

    private boolean exists(ItemWeapon weapon){
        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM weapon WHERE id = " + weapon.getId());

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean create(ItemWeapon weapon) {

        if(exists(weapon)) {
            return false;
        }

        try (Connection connection = dataSource.getConnection()) {

            String query = "INSERT INTO vaapen VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, weapon.id);
            statement.setString(2, weapon.placeDiscovered);
            statement.setInt(3, weapon.finder_id);
            statement.setString(4, weapon.dateFound);
            statement.setInt(5, weapon.expectedYearOfCreation);
            if (weapon.museum_id != 0) {
                statement.setInt(6, weapon.museum_id);
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            statement.setString(7, weapon.getWeaponType());
            statement.setString(8, weapon.getMaterial());
            statement.setInt(9, weapon.getWeight());

            int update = statement.executeUpdate();

            if (update > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ItemWeapon> getAll(){
        try (Connection connection = dataSource.getConnection()) {

            ArrayList<ItemWeapon> itemArray = new ArrayList<>();

            // Load weapons
            Statement statementW = connection.createStatement();

            ResultSet resultWeapons = statementW.executeQuery("SELECT * FROM vaapen");

            while (resultWeapons.next()) {
                String tempMuseumID = null;
                ItemWeapon weapon;
                try {
                    tempMuseumID = resultWeapons.getString("museum_id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (tempMuseumID == null) {
                    weapon = new ItemWeapon(
                            resultWeapons.getInt("id"),
                            resultWeapons.getString("funnsted"),
                            resultWeapons.getInt("finner_id"),
                            resultWeapons.getString("funntidspunkt"),
                            resultWeapons.getInt("antatt_aarstall"),
                            0,
                            "Våpen",
                            resultWeapons.getString("type"),
                            resultWeapons.getString("materiale"),
                            resultWeapons.getInt("vekt")
                    );
                } else {
                    weapon = new ItemWeapon(
                            resultWeapons.getInt("id"),
                            resultWeapons.getString("funnsted"),
                            resultWeapons.getInt("finner_id"),
                            resultWeapons.getString("funntidspunkt"),
                            resultWeapons.getInt("antatt_aarstall"),
                            resultWeapons.getInt("museum_id"),
                            "Våpen",
                            resultWeapons.getString("type"),
                            resultWeapons.getString("materiale"),
                            resultWeapons.getInt("vekt")
                    );
                }

                itemArray.add(weapon);
            }

            return itemArray;
        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }

    public ItemWeapon getById(int id){
        //TODO: Implement
    }
}
