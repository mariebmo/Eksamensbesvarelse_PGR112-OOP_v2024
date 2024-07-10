package history_database;

import java.sql.*;
import java.util.ArrayList;

import history_database.model.FoundItem;
import history_database.model.ItemCoin;
import history_database.model.ItemJewelry;
import history_database.model.ItemWeapon;
import history_database.model.Museum;
import history_database.model.Person;
import history_database.repository.JewelryRepo;
import history_database.repository.PersonRepo;
import history_database.repository.WeaponRepo;

/*
    Klasse som skal håndtere all datahåndtering til og fra database og
    data inn fra fra filscanner
*/

public class DataHandler {

    //# Fields
    private Database database;
    private FileScanner input;
    private ArrayList<Person> peopleArray = new ArrayList<>();
    private ArrayList<Museum> museumArray = new ArrayList<>();
    private ArrayList<FoundItem> itemArray = new ArrayList<>();


    //# Constructor
    DataHandler(Database database) {
        this.database = database;
        input = new FileScanner();
    }

    void parseFile() {
        input.readFile();
    }

    // Methods for adding new data to database
    void addFunnDataToDatabase() {
        input.getPeople().forEach(this::addPersonToDatabase);
        input.getMuseums().forEach(this::addMuseumToDatabase);
        input.getCoins().forEach(this::addCoinToDatabase);
        input.getWeapons().forEach(this::addWeaponToDatabase);
        input.getTrinkets().forEach(this::addJewelryToDatabase);
    }

    private void addPersonToDatabase(Person person) {
        personRepo.create(person);
    }
    
    private void addMuseumToDatabase(Museum museum) {
        museumRepo.create(museum);
    }

    private void addCoinToDatabase(ItemCoin coin) {
        coinRepo.create(coin);
    }

    private void addWeaponToDatabase(ItemWeapon weapon) {
        weaponRepo.create(weapon);
    }

    private void addJewelryToDatabase(ItemJewelry jewelry) {
        jewelryRepo.create(jewelry);
    }
    
    // Methods for printing info about items in Database (that is saved in local memory)
    
    private String getPersonNameBasedOnID(int person_id) {
        return PersonRepo.getById(person_id).getName() ?? "Not found"
    }
    private String getMuseumNameBasedOnID(int museum_id) {
        return MuseumRepo.getById(museum_id).getName() ?? "Not found"
    }

    private int getNumberOfCoinsFound() {
        int count = 0;
        for (FoundItem item : itemArray) {
            if (item instanceof ItemCoin) {
                count++;
            }
        }
        return count;
    }
    private int getNumberOfWeaponsFound() {
        int count = 0;
        for (FoundItem item : itemArray) {
            if (item instanceof ItemWeapon) {
                count++;
            }
        }
        return count;
    }
    private int getNumberOfJewelryFound() {
        int count = 0;
        for (FoundItem item : itemArray) {
            if (item instanceof ItemJewelry) {
                count++;
            }
        }
        return count;
    }

    // Methods for loading data from database into program at start
    void initializeDatabaseData() {
        peopleArray = getPeopleFromDatabase();
        museumArray = getMuseumsFromDatabase();
        getAllItemsFromDatabase();
    }

    private ArrayList<Person> getPeopleFromDatabase() {
        return PersonRepo.getAll();
    }
    
    private ArrayList<Museum> getMuseumsFromDatabase() {
        return MuseumRepo.getAll();
    }
    private void getAllItemsFromDatabase() {
        itemArray.clear();
        itemArray.AddAll(getCoinsFromDatabase())
        itemArray.AddAll(getWeaponsFromDatabase())
        itemArray.AddAll(getJewelryFromDatabase())
    }
    private ArrayList<ItemCoin> getCoinsFromDatabase() {
        return CoinRepo.getAll();
    }
    private ArrayList<ItemWeapon> getWeaponsFromDatabase() {
        return WeaponRepo.getAll();
    }
    private ArrayList<ItemJewelry> getJewelryFromDatabase() {
        return JewelryRepo.getAll()
    }
}