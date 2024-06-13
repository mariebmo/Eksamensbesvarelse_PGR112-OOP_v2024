package history_database;

import java.sql.*;
import java.util.ArrayList;

/*
    Klasse som skal håndtere all datahåndtering til og fra database og
    data fra scanner
 */

public class DataHandler {
    //# Fields
    private Database database;
    private FileScanner input;
    private ArrayList<Person> peopleInDatabase = new ArrayList<>();
    private ArrayList<Museum> museumsInDatabase = new ArrayList<>();
    private ArrayList<FoundItem> itemsInDatabase = new ArrayList<>();


    //# Constructor
    public DataHandler(Database database) {
        this.database = database;
        input = new FileScanner(database);
    }

    void parseFile() {
        input.readFile();
    }

    // Methods for adding new data to database
    public void addNewDataToDatabase() {
        addNewPeopleToDatabaseIfNotDuplicate();
        addNewMuseumsToDatabaseIfNotDuplicate();
        addNewCoinToMuseumIfNotDuplicate();
        addNewWeaponToMuseumIfNotDuplicate();
    }

    private void addNewPeopleToDatabaseIfNotDuplicate() {
        var people = input.getPeople();

        for (Person person : people) {
            var duplicate = false;

            for (Person databasePerson : peopleInDatabase) {
                if (databasePerson.name().equals(person.name())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (addPersonToDatabase(person)) {
                    System.out.println(STR."//$ \{person.name()} added to database");
                }
            }

        }
    }

    private void addNewMuseumsToDatabaseIfNotDuplicate() {
        var museums = input.getMuseums();

        for (Museum museum : museums) {
            var duplicate = false;

            for (Museum databaseMuseum : museumsInDatabase) {
                if (databaseMuseum.name().equals(museum.name())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (addMuseumToDatabase(museum)) {
                    System.out.println(STR."//$ \{museum.name()} added to database");
                }
            }

        }
    }

    private void addNewCoinToMuseumIfNotDuplicate() {
        var coins = input.getCoins();

        for (ItemCoin coin : coins) {
            var duplicate = false;

            for (FoundItem databaseCoin : itemsInDatabase) {
                if (databaseCoin.id == coin.id) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (addCoinToDatabase(coin)) {
                    System.out.println(STR."//$ Coin with ID:\{coin.id} added to database");
                }
            }

        }
    }

    private void addNewWeaponToMuseumIfNotDuplicate() {
        var weapons = input.getWeapons();

        for (ItemWeapon weapon : weapons) {
            var duplicate = false;

            for (FoundItem databaseWeapon : itemsInDatabase) {
                if (databaseWeapon.id == weapon.id) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (addWeaponToDatabase(weapon)) {
                    System.out.println(STR."//$ Weapon with ID:\{weapon.id} added to database");
                }
            }

        }
    }

    private boolean addPersonToDatabase(Person person) {
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

    private boolean addMuseumToDatabase(Museum museum) {
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

    private boolean addCoinToDatabase(ItemCoin coin) {
        try (Connection connection = database.getConnection()) {

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

    private boolean addWeaponToDatabase(ItemWeapon weapon) {
        try (Connection connection = database.getConnection()) {

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

    // Methods for printing info about items in Database (that is saved in local memory)
    void printAllCoins() {
        System.out.println("*** MYNTER FUNNET ***");
        int count = 1;
        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemCoin) {
                ItemCoin coin = (ItemCoin) item;

                System.out.print(STR."Mynt #\{count} fra rundt år \{coin.expectedYearOfCreation} (ID: \{coin.id}). ");
                System.out.println(STR."Funnet av \{getPersonNameBasedOnID(coin.finder_id)} i \{coin.dateFound.substring(0, 4)}.");
                System.out.println(STR."- \{coin.getDiameter()} mm i diameter og lagd av \{coin.getMetal().toLowerCase()}.");
                if (coin.museum_id != 0) {
                    System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(coin.museum_id)}.");
                } else {
                    System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i en boks i kjelleren på klubbhuset.");
                }
                System.out.println("");

                count++;
            }
        }
    }

    void printAllJewelry() {
        System.out.println("*** SMYKKER FUNNET ***");
        int count = 1;

        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemJewelry) {
                ItemJewelry jewelry = (ItemJewelry) item;

                System.out.print(STR."Smykke #\{count}: \{jewelry.getJewelryType()} fra rundt år \{jewelry.expectedYearOfCreation} (ID: \{jewelry.id}). ");
                System.out.println(STR."Funnet av \{getPersonNameBasedOnID(jewelry.finder_id)} i \{jewelry.dateFound.substring(0, 4)}.");
                System.out.println(STR."- Verdi estimert til \{jewelry.getValueEstimate()} kroner (se bilde: \{jewelry.getImageFilename()}).");
                if (jewelry.museum_id != 0) {
                    System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(jewelry.museum_id)}.");
                } else {
                    System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i safen på klubbhuset.");
                }
                System.out.println("");
                count++;
            }
        }
    }

    void printAllWeapons() {
        System.out.println("*** VÅPEN FUNNET ***");
        int count = 1;

        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemWeapon) {
                ItemWeapon weapon = (ItemWeapon) item;

                System.out.print(STR."Våpen #\{count}: \{weapon.getWeaponType()} fra rundt år \{weapon.expectedYearOfCreation} (ID: \{weapon.id}). ");
                System.out.println(STR."Funnet av \{getPersonNameBasedOnID(weapon.finder_id)} i \{weapon.dateFound.substring(0, 4)}.");
                System.out.println(STR."- Lagd av \{weapon.getMaterial()} og veier \{weapon.getWeight()} gram.");
                if (weapon.museum_id != 0) {
                    System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(weapon.museum_id)}.");
                } else {
                    System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i safen på klubbhuset.");
                }
                System.out.println("");
                count++;
            }
        }
    }

    void printItemsOlderThanX(int chosenYear) {
        ArrayList<FoundItem> tempItems = new ArrayList<>();

        for (FoundItem item : itemsInDatabase) {
            if (item.getExpectedYearOfCreation() < chosenYear) {
                tempItems.add(item);
            }
        }

        System.out.println(STR."Vi har funnet totalt funnet \{tempItems.size()} gjenstander som er eldre enn \{chosenYear}:");
        System.out.println("");

        int counter = 1;
        for (FoundItem oldItem : tempItems) {
            System.out.println(STR."Gjenstand #\{counter}/\{tempItems.size()}");
            printItemSwitch(oldItem);
            System.out.println("");
            counter++;
        }
    }

    private void printItemSwitch(FoundItem item) {
        switch (item.type) {
            case "Mynt" -> printItemIfCoin(item);
            case "Smykke" -> printItemIfJewelry(item);
            case "Våpen" -> printItemIfWeapon(item);
        }
    }

    private void printItemIfCoin(FoundItem item) {
        ItemCoin coin = (ItemCoin) item;

        System.out.print(STR."• Mynt fra rundt år \{coin.expectedYearOfCreation} (ID: \{coin.id}). ");
        System.out.println(STR."Funnet av \{getPersonNameBasedOnID(coin.finder_id)} i \{coin.dateFound.substring(0, 4)}.");
        System.out.println(STR."- \{coin.getDiameter()} mm i diameter og lagd av \{coin.getMetal().toLowerCase()}.");
        if (coin.museum_id != 0) {
            System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(coin.museum_id)}.");
        } else {
            System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i en boks i kjelleren på klubbhuset.");
        }
    }

    private void printItemIfWeapon(FoundItem item) {
        ItemWeapon weapon = (ItemWeapon) item;

        System.out.print(STR."• \{weapon.getWeaponType()} fra rundt år \{weapon.expectedYearOfCreation} (ID: \{weapon.id}). ");
        System.out.println(STR."Funnet av \{getPersonNameBasedOnID(weapon.finder_id)} i \{weapon.dateFound.substring(0, 4)}.");
        System.out.println(STR."- Lagd av \{weapon.getMaterial()} og veier \{weapon.getWeight()} gram.");
        if (weapon.museum_id != 0) {
            System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(weapon.museum_id)}.");
        } else {
            System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i safen på klubbhuset.");
        }
    }

    private void printItemIfJewelry(FoundItem item) {
        ItemJewelry jewelry = (ItemJewelry) item;

        System.out.print(STR."• Smykke: \{jewelry.getJewelryType()} fra rundt år \{jewelry.expectedYearOfCreation} (ID: \{jewelry.id}). ");
        System.out.println(STR."Funnet av \{getPersonNameBasedOnID(jewelry.finder_id)} i \{jewelry.dateFound.substring(0, 4)}.");
        System.out.println(STR."- Verdi estimert til \{jewelry.getValueEstimate()} kroner (se bilde: \{jewelry.getImageFilename()}).");
        if (jewelry.museum_id != 0) {
            System.out.println(STR."- For øyeblikket utstilt på \{getMuseumNameBasedOnID(jewelry.museum_id)}.");
        } else {
            System.out.println("- Ikke utstilt på museum for øyeblikket. Så ligger i safen på klubbhuset.");
        }
    }

    void printNumbersAboutItems() {
        System.out.println("*** INFO RUNDT ANTALL GJENSTANDER FUNNET ***");
        System.out.println(STR."- Totalt har vi funnet \{itemsInDatabase.size()} gjenstander siden vi startet gruppen.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfCoinsFound()} mynter.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfWeaponsFound()} våpen.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfJewelryFound()} smykker.");
        System.out.println("");
    }

    private String getPersonNameBasedOnID(int person_id) {
        for (Person person : peopleInDatabase) {
            if (person.id() == person_id) {
                return person.name();
            }
        }
        return null;
    }

    private String getMuseumNameBasedOnID(int museum_id) {
        for (Museum museum : museumsInDatabase) {
            if (museum.id() == museum_id) {
                return museum.name();
            }
        }
        return null;
    }

    private int getNumberOfCoinsFound() {
        int count = 0;
        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemCoin) {
                count++;
            }
        }
        return count;
    }

    private int getNumberOfWeaponsFound() {
        int count = 0;
        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemWeapon) {
                count++;
            }
        }
        return count;
    }

    private int getNumberOfJewelryFound() {
        int count = 0;
        for (FoundItem item : itemsInDatabase) {
            if (item instanceof ItemJewelry) {
                count++;
            }
        }
        return count;
    }

    // Methods for loading data from database into program at start
    public void loadDataFromDatabaseAtStart() {
        loadPeopleFromDatabase();
        loadMuseumsFromDatabase();
        loadItemsFromDatabase();
    }

    public void loadPeopleFromDatabase() {
        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

            while (resultSet.next()) {
                var person = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("navn"),
                        resultSet.getInt("tlf"),
                        resultSet.getString("e_post")
                );

                peopleInDatabase.add(person);
            }

            System.out.println(STR."//$ \{peopleInDatabase.size()} people loaded from database");

        } catch (SQLException e) {
            System.out.println("Could not load people from database");
            throw new RuntimeException(e);
        }
    }

    public void loadMuseumsFromDatabase() {
        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM museum");

            while (resultSet.next()) {
                var museum = new Museum(
                        resultSet.getInt("id"),
                        resultSet.getString("navn"),
                        resultSet.getString("sted")
                );

                museumsInDatabase.add(museum);
            }

            System.out.println(STR."//$ \{museumsInDatabase.size()} museums loaded from database");

        } catch (SQLException e) {
            System.out.println("Could not load people from database");
            throw new RuntimeException(e);
        }
    }

    public void loadItemsFromDatabase() {
        loadCoinsFromDatabase();
        loadWeaponsFromDatabase();
        loadJewelryFromDatabase();
    }
    private void loadCoinsFromDatabase() {
        try (Connection connection = database.getConnection()) {

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

                itemsInDatabase.add(coin);
            }
        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }
    private void loadWeaponsFromDatabase() {
        try (Connection connection = database.getConnection()) {

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

                itemsInDatabase.add(weapon);
            }
        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }
    private void loadJewelryFromDatabase() {
        try (Connection connection = database.getConnection()) {

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

                itemsInDatabase.add(jewelry);
            }


            System.out.println(STR."\{itemsInDatabase.size()} items loaded from database");

        } catch (SQLException e) {
            System.out.println("Could not load items from database");
            throw new RuntimeException(e);
        }
    }
}