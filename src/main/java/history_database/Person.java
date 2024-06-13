package history_database;

public class Person
{
    //# Fields
    private int id;
    private String name;
    private int phone_number;
    private String email;

    //# Constructor
    public Person(int id, String name, int phone_number, String email) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPhone_number() {
        return this.phone_number;
    }

    public String getEmail() {
        return this.email;
    }
}
