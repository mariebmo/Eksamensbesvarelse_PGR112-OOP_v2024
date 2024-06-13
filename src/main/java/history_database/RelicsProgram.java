package history_database;

import java.util.Scanner;

public class RelicsProgram
{
    //# Fields
    private final DataHandler data;
    private final static Scanner input = new Scanner(System.in);

    //# Constructor
    public RelicsProgram (DataHandler data) {
        this.data = data;
    }

    //# Methods
    public void startProgram() {
        System.out.println("Program is running");
    }
}
