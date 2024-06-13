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
        System.out.println("Program is booting up...");

        data.loadDataFromDatabaseAtStart();
        data.parseFile();
        data.addNewDataToDatabase();

        System.out.println("Program finished loading, starting now...");
        printDivider();

        showMenu();
    }

    private void showMenu() {
        System.out.println("*** HOVEDMENY ***");
        System.out.println("1. Se informasjon om alle gjenstander funnet.");
        System.out.println("2. Se informasjon om alle gjenstander over en viss alder.");
        System.out.println("3. Se informasjon om antall gjenstander registrert.");
        System.out.println("4. Avslutt programmet.");
        System.out.print("Skriv inn ditt menyvalg her: ");

        int userInput = checkInputIfValidNumber(4);


    }

    private void printDivider() {
        System.out.println("\n----------\n");
    }

    /*
       Denne koden er basert på getNumberFromTerminalInput() fra foreleser Marcus Alexander Dahl i prosjektet Terminal
       Link: https://github.com/kristiania/PGR112v24/blob/main/code/solutions/database/terminal/src/Terminal.java
     */
    public int checkInputIfValidNumber(int maxMenuNumber) {
        try {
            int userInput = Integer.parseInt(input.nextLine());
            if (userInput <= maxMenuNumber) {
                return userInput;
            } else {
                System.out.println(STR."Menyen har kun valg mellom 1-\{maxMenuNumber}. Prøv igjen: ");

                return checkInputIfValidNumber(maxMenuNumber);
            }
        } catch (NumberFormatException e) {
            System.out.println(STR."Menyen støtter kun tall mellom 1-\{maxMenuNumber}. Prøv igjen: ");

            return checkInputIfValidNumber(maxMenuNumber);
        }
    }
}
