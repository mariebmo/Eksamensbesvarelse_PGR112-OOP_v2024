package history_database;

import java.util.Scanner;

/*
    Klasse for å håndtere menysystemet og brukerinput gjennom terminalen
 */

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
        System.out.println("...Program is booting up...");

        data.parseFile();
        data.loadDataFromDatabaseAtStart();
        data.addNewDataToDatabase();
        data.loadDataFromDatabaseAtStart();

        System.out.println(STR."...\{data.getAmountOfPeopleInDatabase()} people loaded from database...");
        System.out.println(STR."...\{data.getAmountOfMuseumsInDatabase()} museums loaded from database...");
        System.out.println(STR."...\{data.getAmountOfItemsInDatabase()} items loaded from database...");

        System.out.println("Program finished loading, starting now...");

        showMenu();
    }

    private void showMenu() {
        printDivider();
        System.out.println("*** HOVEDMENY ***");
        System.out.println("1. Se informasjon om alle gjenstander funnet.");
        System.out.println("2. Se informasjon om alle gjenstander over en viss alder.");
        System.out.println("3. Se informasjon om antall gjenstander registrert.");
        System.out.println("4. Avslutt programmet.");
        System.out.print("Skriv inn ditt menyvalg her: ");

        int userInput = checkInputIfValidNumber(4);

        switch (userInput) {
            case 1 -> {
                printDivider();
                menu1_printInfoAboutAllItems();
            }
            case 2 -> {
                printDivider();
                menu2_printInfoBasedOnAge();
            }
            case 3 -> {
                printDivider();
                menu3_printInfoBasedOnNumbers();
            }
            case 4 -> {
                printDivider();
                System.out.println("Programmet avsluttes... Velkommen tilbake!");
                System.exit(0);
            }
            default -> showMenu();
        }
    }

    private void menu1_printInfoAboutAllItems() {
        System.out.println("Oversikt over alt vi har... Det er mye det...\n");

        printAllCoins();
        printAllJewelry();
        printAllWeapons();

        printArrowUpText();
        System.out.println(STR." Info om \{data.getAmountOfItemsInDatabase()} gjenstander printet ut.");
        showMenu();
    }
    private void menu2_printInfoBasedOnAge() {
        System.out.println("*** GJENSTANDER OVER EN VISS ALDER ***");
        System.out.println("Denne menyen er viser alle gjenstander eldre enn et bestemt årstall.");
        System.out.println("Hvilket årstall velger du?: ");

        int userInput = checkInputIfValidNumber(2024);
        System.out.println("");

        data.printItemsOlderThanX(userInput);

        printArrowUpText();
        System.out.println();
        showMenu();
    }
    private void menu3_printInfoBasedOnNumbers() {
        printNumbersAboutItems();

        printArrowUpText();
        System.out.println();
        showMenu();
    }

    private void printNumbersAboutItems() {
        data.printNumbersAboutItems();
    }

    private void printAllCoins() {
        data.printAllCoins();
    }
    private void printAllJewelry() { data.printAllJewelry(); }
    private void printAllWeapons() { data.printAllWeapons(); }

    // methods that do small odd jobs related to the menu system
    private void printDivider() {
        System.out.println("\n----------\n");
    }
    private void printArrowUpText() {
        System.out.print("---> Se resultatet ditt over ↑");
    }
    /*
       Denne koden er basert på getNumberFromTerminalInput() fra foreleser Marcus Alexander Dahl i prosjektet Terminal
       Link: https://github.com/kristiania/PGR112v24/blob/main/code/solutions/database/terminal/src/Terminal.java
     */
    private int checkInputIfValidNumber(int maxMenuNumber) {
        try {
            int userInput = Integer.parseInt(input.nextLine());
            if (userInput <= maxMenuNumber) {
                return userInput;
            } else {
                System.out.print(STR."Menyen har kun menyvalg mellom 1-\{maxMenuNumber}. Prøv igjen: ");

                return checkInputIfValidNumber(maxMenuNumber);
            }
        } catch (NumberFormatException e) {
            System.out.print(STR."Menyen støtter kun tall mellom 1-\{maxMenuNumber}. Prøv igjen: ");

            return checkInputIfValidNumber(maxMenuNumber);
        }
    }

    void printAllCoins() {
        System.out.println("*** MYNTER FUNNET ***");
        int count = 1;
        for (FoundItem item : itemArray) {
            if (item instanceof ItemCoin) {
                ItemCoin coin = (ItemCoin) item;

                // Dette bør være en .toString() metode i ItemCoin klassen

                System.out.print(STR."Mynt #\{count}/\{getNumberOfCoinsFound()} fra rundt år \{coin.expectedYearOfCreation} (ID: \{coin.id}). ");
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

        for (FoundItem item : itemArray) {
            if (item instanceof ItemJewelry) {
                ItemJewelry jewelry = (ItemJewelry) item;

                // Dette bør være en .toString() metode i Jewelry klassen
                System.out.print(STR."Smykke #\{count}/\{getNumberOfJewelryFound()}: \{jewelry.getJewelryType()} fra rundt år \{jewelry.expectedYearOfCreation} (ID: \{jewelry.id}). ");
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

        for (FoundItem item : itemArray) {
            if (item instanceof ItemWeapon) {
                ItemWeapon weapon = (ItemWeapon) item;

                // Dette bør være en .toString() metode i Weapon klassen
                System.out.print(STR."Våpen #\{count}/\{getNumberOfWeaponsFound()}: \{weapon.getWeaponType()} fra rundt år \{weapon.expectedYearOfCreation} (ID: \{weapon.id}). ");
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

        for (FoundItem item : itemArray) {
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
        System.out.println(STR."• Gruppen vår har \{peopleArray.size()} hobby-arkiologer som har funnet gjenstander.");
        System.out.println(STR."• Gjenstander vi har funnet er utstilt på hele \{museumArray.size()} forskjellige museum.");
        System.out.println(STR."• Totalt har vi funnet \{itemArray.size()} gjenstander siden vi startet gruppen.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfCoinsFound()} mynter.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfWeaponsFound()} våpen.");
        System.out.println(STR."--> Vi har funnet \{getNumberOfJewelryFound()} smykker.");
        System.out.println("");
    }

}
