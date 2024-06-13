package history_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
    Klasse som skal håndtere all datahåndtering til og fra database og
    data fra scanner
 */
public class DataHandler
{
    //# Fields
    private Database database;
    private FileScanner input;
    private ArrayList<Person> peopleInDatabase = new ArrayList<>();


    //# Constructor
    public DataHandler(Database database) {
        this.database = database;
        input = new FileScanner(database);
    }

    void parseFile() {
        input.readFile();
    }

    public void addNewPeopleToDatabase() {
        var people = input.getPeople();

        for (Person person : people) {

        }
    }

    // Methods for loading data from database into program at start
    public void loadDataFromDatabaseAtStart() {
        loadPeopleFromDatabase();
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

}
