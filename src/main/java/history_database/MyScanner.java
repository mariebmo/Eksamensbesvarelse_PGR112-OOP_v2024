package history_database;

import java.io.File;
import java.util.Scanner;

public class MyScanner
{
    //# Fields
    private Database database;
    private File text;
    private Scanner input;

    // Constructor
    public MyScanner(Database database) {
        this.database = database;
        this.text = new File("src/main/resources/funn.txt");
    }

    void parseFile() {
        if (text.exists() && text.isFile()) {
            System.out.println("Found the file");
        } else {
            throw new RuntimeException("Couldn't find the file in resources folder: funn.txt");
        }
    }


}
