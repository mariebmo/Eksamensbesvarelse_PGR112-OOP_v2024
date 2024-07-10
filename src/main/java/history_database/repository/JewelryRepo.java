package history_database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import history_database.model.ItemJewelry;
import history_database.model.Museum;

public class JewelryRepo {
    protected DataSource dataSource;

    public JewelryRepo() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3307/Funn");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");

        this.dataSource = mysqlDataSource;
    }

    private boolean exists(ItemJewelry jewelry){
        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM jewelry WHERE id = " + jewelry.getId());

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean create(ItemJewelry jewelry) {

        if(exists(jewelry)) {
            return false;
        }

        try (Connection connection = dataSource.getConnection()) {

            String query = "INSERT INTO smykke VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, jewelry.id);
            statement.setString(2, jewelry.placeDiscovered);
            statement.setInt(3, jewelry.finder_id);
            statement.setString(4, jewelry.dateFound);
            statement.setInt(5, jewelry.expectedYearOfCreation);
            if (jewelry.museum_id != 0) {
                statement.setInt(6, jewelry.museum_id);
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            statement.setString(7, jewelry.getJewelryType());
            statement.setInt(8, jewelry.getValueEstimate());
            statement.setString(9, jewelry.getImageFilename());

            int update = statement.executeUpdate();

            if (update > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ItemJewelry> getAll(){
        try (Connection connection = database.getConnection()) {

            ArrayList<ItemJewelry> itemArray = new ArrayList<>();

            // Load jewelry
            Statement statementJ = connection.createStatement();

            ResultSet resultJewelry = statementJ.executeQuery("SELECT * FROM smykke");

            while (resultJewelry.next()) {
                String tempMuseumID = null;
                ItemJewelry jewelry;

                try {
                    tempMuseumID = resultJewelry.getString("museum_id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (tempMuseumID == null) {
                    jewelry = new ItemJewelry(
                            resultJewelry.getInt("id"),
                            resultJewelry.getString("funnsted"),
                            resultJewelry.getInt("finner_id"),
                            resultJewelry.getString("funntidspunkt"),
                            resultJewelry.getInt("antatt_aarstall"),
                            0,
                            "Smykke",
                            resultJewelry.getString("type"),
                            resultJewelry.getInt("verdiestimat"),
                            resultJewelry.getString("filnavn")
                    );
                } else {
                    jewelry = new ItemJewelry(
                            resultJewelry.getInt("id"),
                            resultJewelry.getString("funnsted"),
                            resultJewelry.getInt("finner_id"),
                            resultJewelry.getString("funntidspunkt"),
                            resultJewelry.getInt("antatt_aarstall"),
                            resultJewelry.getInt("museum_id"),
                            "Smykke",
                            resultJewelry.getString("type"),
                            resultJewelry.getInt("verdiestimat"),
                            resultJewelry.getString("filnavn")
                    );
                }

                itemArray.add(jewelry);
            }

            return itemArray;

        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }

    public ItemJewelry getById(int id){
        //TODO: Implement
    }
}
