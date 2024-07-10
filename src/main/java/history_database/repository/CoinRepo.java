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
import history_database.model.Museum;

public class CoinRepo {
    protected DataSource dataSource;

    public CoinRepo() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3307/Funn");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");

        this.dataSource = mysqlDataSource;
    }

    private boolean exists(ItemCoin coin){
        try (Connection con = dataSource.getConnection()) {
            Statement s = con.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM coin WHERE id = " + coin.getId());

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean create(ItemCoin coin) {

        if(exists(coin)) {
            return false;
        }

        try (Connection connection = dataSource.getConnection()) {

            String query = "INSERT INTO mynt VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, coin.id);
            statement.setString(2, coin.placeDiscovered);
            statement.setInt(3, coin.finder_id);
            statement.setString(4, coin.dateFound);
            statement.setInt(5, coin.expectedYearOfCreation);
            if (coin.museum_id != 0) {
                statement.setInt(6, coin.museum_id);
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            statement.setInt(7, coin.getDiameter());
            statement.setString(8, coin.getMetal());

            int update = statement.executeUpdate();

            if (update > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     private ArrayList<ItemCoin> getAll() {
        try (Connection connection = dataSource.getConnection()) {

            ArrayList<ItemCoin> coinArray = new ArrayList<>();

            // Load coins
            Statement statementC = connection.createStatement();

            ResultSet resultCoins = statementC.executeQuery("SELECT * FROM mynt");

            while (resultCoins.next()) {
                String tempMuseumID = null;
                ItemCoin coin;
                try {
                    tempMuseumID = resultCoins.getString("museum_id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (tempMuseumID == null) {
                    coin = new ItemCoin(
                            resultCoins.getInt("id"),
                            resultCoins.getString("funnsted"),
                            resultCoins.getInt("finner_id"),
                            resultCoins.getString("funntidspunkt"),
                            resultCoins.getInt("antatt_aarstall"),
                            0,
                            "Mynt",
                            resultCoins.getInt("diameter"),
                            resultCoins.getString("metall")
                    );
                } else {
                    coin = new ItemCoin(
                            resultCoins.getInt("id"),
                            resultCoins.getString("funnsted"),
                            resultCoins.getInt("finner_id"),
                            resultCoins.getString("funntidspunkt"),
                            resultCoins.getInt("antatt_aarstall"),
                            resultCoins.getInt("museum_id"),
                            "Mynt",
                            resultCoins.getInt("diameter"),
                            resultCoins.getString("metall")
                    );
                }

                itemArray.add(coin);
            }

            return coinArray;
        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }

    public ItemCoin getById(int id){
        //TODO: Implement
    }
}
