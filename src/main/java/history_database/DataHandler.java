package history_database;

import history_database.data_in_out.MyScanner;

/*
    Klasse som skal håndtere all datahåndtering til og fra database og
    data fra scanner
 */
public class DataHandler
{
    //# Fields
    private Database database;
    private MyScanner input;

    //# Constructor
    public DataHandler(Database database) {
        this.database = database;
        input = new MyScanner(database);
    }

}
