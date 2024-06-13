package history_database;

import java.io.File;
import java.io.FileNotFoundException;
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

    // Constructor
    public MyScanner(Database database) {
        this.database = database;
        this.text = new File("src/main/resources/funn.txt");
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

            while (input.hasNextLine()) {
                line = input.nextLine();

                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong during parsing of the .txt-file...");
            throw new RuntimeException(e);
        }

        System.out.println("Parsing of Funn.txt is done.");
    }


}
