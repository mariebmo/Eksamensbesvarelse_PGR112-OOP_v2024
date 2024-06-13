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
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Museum> museums = new ArrayList<>();
    private ArrayList<ItemCoin> coins = new ArrayList<>();
    private ArrayList<ItemWeapon> weapons = new ArrayList<>();
    private ArrayList<ItemJewelry> trinkets = new ArrayList<>();


    // Constructor
    public MyScanner(Database database) {
        this.database = database;
        this.text = new File("src/main/resources/funn.txt");
    }

    //# Getters
    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<Museum> getMuseums() {
        return museums;
    }

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
            String previousLine = "none";
            String topic = "none";
            int counter = 0;

            int numberOfPeople = 0;

            int numberOfMuseums = 0;

            System.out.println("----------");

            while (input.hasNextLine()) {
                int id;
                String name;
                int phone_number;
                String email;
                String location;

                String coordinates;
                int finder_id;
                String dateFound;
                int expectedYearOfCreation;
                int museum_id;
                String type;

                int coinDiameter;
                String coinMetal;

                String weaponType;
                String weaponMaterial;
                int weaponWeight;

                String jewelryType;
                int jewelryValue;
                String jewelryImagePath;

                counter++;
                line = input.nextLine();

                // sjekker om det er en overskrift basert på om linjen slutter på ":"
                if (line.contains(":")) {
                    topic = line.replace(":", "");

                    System.out.println(STR."\n//$ Found new topic \"\{topic}\"");

                    previousLine = line;

                    continue;
                }

                // Alt som omhandler personer i txt-filen
                if (topic.equals("Personer")) {

                    // hopper over linjen med antall personer i listen
                    if (previousLine.equals("Personer:") && numberOfPeople == 0) {
                        previousLine = line;
                        numberOfPeople = Integer.parseInt(line);
                        continue;
                    }

                    // henter ut info om personen
                    id = convertToNumber(line);
                    name = input.nextLine();
                    phone_number = convertToNumber(input.nextLine());
                    email = input.nextLine();

                    var person = new Person(id, name, phone_number, email);
                    // System.out.println(STR."Person \{person.id()} lagt til (\{person.name()}, \{person.phone_number()}, \{person.email()})");

                    people.add(person);
                }

                // Alt som omhandler museum i txt-filen
                if (topic.equals("Museer")) {

                    // Hopper over linjen med antall museer i filen
                    if (previousLine.equals("Museer:") && numberOfMuseums == 0) {
                        previousLine = line;
                        numberOfMuseums = Integer.parseInt(line);
                        continue;
                    }

                    // Henter ut info om museet
                    id = convertToNumber(line);
                    name = input.nextLine();
                    location = input.nextLine();

                    var museum = new Museum(id, name, location);

                    // System.out.println(STR."Museum #\{museum.id()} lagt til (\{museum.name()}, \{museum.location()})");

                    museums.add(museum);
                }

                //# Alt som omhandler gjenstander i txt-filen
                if (topic.equals("Funn")) {

                    id = convertToNumber(line);
                    coordinates = input.nextLine();
                    finder_id = convertToNumber(input.nextLine());
                    dateFound = input.nextLine();
                    expectedYearOfCreation = convertToNumber(input.nextLine());
                    String possibleMuseum = input.nextLine();
                    if (possibleMuseum.isEmpty()) {
                        museum_id = 0;
                    } else {
                        museum_id = convertToNumber(possibleMuseum);
                    }
                    type = input.nextLine();

                    // Legger til ny mynt
                    if (type.equals("Mynt")) {
                        coinDiameter = convertToNumber(input.nextLine());
                        coinMetal = input.nextLine();

                        ItemCoin coin;

                        if (museum_id == 0) {
                            coin = new ItemCoin(id,coordinates,finder_id,dateFound,expectedYearOfCreation,type,coinDiameter,coinMetal);
                        } else {
                            coin = new ItemCoin(id,coordinates,finder_id,dateFound,expectedYearOfCreation,museum_id,type,coinDiameter,coinMetal);
                        }

                        coins.add(coin);

                        if (input.nextLine().contains("-")) {
                            continue;
                        }
                    }

                    // Legger til nytt våpen
                    if (type.equals("Våpen")) {
                        weaponType = input.nextLine();
                        weaponMaterial = input.nextLine();
                        weaponWeight = convertToNumber(input.nextLine());

                        ItemWeapon weapon;

                        if (museum_id == 0) {
                            weapon = new ItemWeapon(id,coordinates,finder_id,dateFound,expectedYearOfCreation, type, weaponType, weaponMaterial, weaponWeight);
                        } else {
                            weapon = new ItemWeapon(id,coordinates,finder_id,dateFound,expectedYearOfCreation,museum_id,type, weaponType, weaponMaterial, weaponWeight);
                        }

                        weapons.add(weapon);

                        if (input.nextLine().contains("-")) {
                            continue;
                        }
                    }

                    if (type.equals("Smykke")) {
                        jewelryType = input.nextLine();
                        jewelryValue = convertToNumber(input.nextLine());
                        jewelryImagePath = input.nextLine();

                        ItemJewelry jewelry;

                        if (museum_id == 0) {
                            jewelry = new ItemJewelry(id,coordinates,finder_id,dateFound,expectedYearOfCreation, type, jewelryType, jewelryValue, jewelryImagePath);
                        } else {
                            jewelry = new ItemJewelry(id,coordinates,finder_id,dateFound,expectedYearOfCreation,museum_id,type, jewelryType, jewelryValue, jewelryImagePath);
                        }

                        trinkets.add(jewelry);

                        if (input.nextLine().contains("-")) {
                            continue;
                        }
                    }

                }

                previousLine = line;
            }

        } catch (FileNotFoundException e) {
            System.out.println("//$ Something went wrong during parsing of the .txt-file...");
            throw new RuntimeException(e);
        }

        System.out.println("\n//$ Parsing of Funn.txt is done.");
        System.out.println(STR."//$ \{people.size()} people added from .txt-file");
        System.out.println(STR."//$ \{museums.size()} museums added from .txt-file");
        System.out.println(STR."//$ \{coins.size()} coins added from .txt-file");
        System.out.println(STR."//$ \{weapons.size()} weapons added from .txt-file");
        System.out.println(STR."//$ \{trinkets.size()} pieces of jewelry added from .txt-file");
    }

    int convertToNumber(String text) {
        return Integer.parseInt(text);
    }

}
