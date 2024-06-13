package history_database;

public class Main
{
    static final Database database = new Database("src/main/resources/database.properties");

    public static void main(String[] args) {
        var data = new DataHandler(database);
        var program = new RelicsProgram(data);

        program.startProgram();
    }
}
