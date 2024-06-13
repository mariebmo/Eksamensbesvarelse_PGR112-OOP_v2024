package history_database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Deler av koden i denne klassen er basert på kode fra Marcus Alexander Dahl i filen:
    https://github.com/kristiania/PGR112v24/code/lectures/_24/Progress.java
 */

public class MyScanner
{
    //# Fields
    private Database database;
    private File text;
    private ArrayList<ItemCoin> coins = new ArrayList<>();
    private ArrayList<ItemWeapon> weapons = new ArrayList<>();
    private ArrayList<ItemJewelry> trinkets = new ArrayList<>();


    // Constructor
    public MyScanner(Database database) {
        this.database = database;
        this.text = new File("src/main/resources/funn.txt");
    }

    //# Getters
    public ArrayList<ItemCoin> getCoins() {
        return coins;
    }

    public ArrayList<ItemWeapon> getWeapons() {
        return weapons;
    }

    public ArrayList<ItemJewelry> getTrinkets() {
        return trinkets;
    }

    //# Methods
    void readFile() {
        if (text.exists() && text.isFile()) {
            System.out.println("Found the file");

            parseFile();

        } else {
            throw new RuntimeException("Couldn't find the file in resources folder: funn.txt");
        }
    }

    void parseFile() {
        try (var input = new Scanner(text)) {

            String line;
            String topic;
            boolean foundTopic = false;

            int id;

            String name;
            int phone_number;
            String email;

            String location;

            String placeDiscovered;
            int finder_id;
            String dateFound;
            int expectedYearOfCreation;
            int museum_id;
            String type;

            System.out.println("----------");

            while (input.hasNextLine()) {
                line = input.nextLine();
                // System.out.println(line);

                if (line.contains(":")) {
                    topic = line.replace(":", "");

                    System.out.println(STR."Found new topic \"\{topic}\"");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong during parsing of the .txt-file...");
            throw new RuntimeException(e);
        }

        System.out.println("Parsing of Funn.txt is done.");
    }

}
