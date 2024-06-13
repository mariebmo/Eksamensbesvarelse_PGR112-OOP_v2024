package history_database;

public abstract class FoundItem
{
    //# Fields
    protected int id;
    protected String placeDiscovered;
    protected int finder_id;
    protected String dateFound;
    protected int expectedYearOfCreation;
    protected int museum_id;
    protected String type;

    //# Constructor
    public FoundItem(int id, String placeDiscovered, int finder_id, String dateFound, int expectedYearOfCreation, int museum_id, String type) {
        this.id = id;
        this.placeDiscovered = placeDiscovered;
        this.finder_id = finder_id;
        this.dateFound = dateFound;
        this.expectedYearOfCreation = expectedYearOfCreation;
        this.museum_id = museum_id;
        this.type = type;
    }

    //# Getters
    public int getId() {
        return id;
    }

    public String getPlaceDiscovered() {
        return placeDiscovered;
    }

    public int getFinder_id() {
        return finder_id;
    }

    public String getDateFound() {
        return dateFound;
    }

    public int getExpectedYearOfCreation() {
        return expectedYearOfCreation;
    }

    public int getMuseum_id() {
        return museum_id;
    }

    public String getType() {
        return type;
    }
}
